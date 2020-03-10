import {$} from "./utils.js";
import {createTemplateTree} from "./template.tree.js";
import EDITOR_CONFIG from './editor.config.js'

const ue = UE.getEditor('editor', EDITOR_CONFIG),
    exportTemplate = `<!DOCTYPE html>
                  <html lang="en" xmlns:th="http://www.thymeleaf.org">
                    <head>
                    <meta charset="UTF-8" http-equiv="Content-Type" content="application/msword"/>
                    <meta name="template-type" content="{templateType}">
                    </head>
                    <body>{html}</body>
                   </html>`;
const domUtils = UE.dom.domUtils;
let colorIndex = 1;

function addGenerateButton() {
    const wrapper = document.createElement('div');
    wrapper.className = 'template-generate-btn-wrapper';
    const button = document.createElement('button');
    button.innerText = '生成';
    button.className = 'template-generate-btn';
    button.addEventListener('click', generateTemplate);
    wrapper.append(button);
    $('.edui-editor-toolbarboxinner').append(wrapper)
}

function removeBorder() {
    if (isPreview) {
        return;
    }
    isPreview = true;
    const localStorageKey = 'template-ueditor-key', html = ue.body.innerHtml;
    ue.removeClassFile('/js/ueditor/themes/iframe.css');
    setTimeout(function(){
        ue.loadClassFile('/js/ueditor/themes/iframe.css');
        ue.body.innerHtml = html;
        ue.setEnabled();
        isPreview = false;
    }, 5000);
    ue.setDisabled();
    ue.setContent(html);
}

function formatDateAndMonth(number) {
    const value = '00' + number;
    return value.substring(value.length - 2, value.length);
}

function generateTemplate() {
    // const html = exportTemplate.replace('{templateType}', templateTree.type).replace('{html}',
    //     ue.getContent()),
    const date = new Date();
    ue.removeClassFile('/js/ueditor/themes/iframe.css');
    const html = ue.getAllHtml();
    ue.loadClassFile('/js/ueditor/themes/iframe.css');
    const filename = `Swagger转doc模板_${date.getFullYear()}${formatDateAndMonth(date.getMonth() + 1)}${formatDateAndMonth(date.getDate())}.html`;
    const a = document.createElement('a');
    const url = URL.createObjectURL(new Blob([html], {type: 'text/html'}));
    a.href = url;
    a.download = filename;
    a.click();
    URL.revokeObjectURL(url);
}

function initDefaultTemplate() {
    fetch('/template/swagger-default-template.html', {
        method: 'GET'
    })
        .then((res) => res.blob())
        .then((blob) => {
            const reader = new FileReader();
            reader.readAsText(blob, 'UTF-8');
            reader.onloadend = () => {
                const bodyStart = '<body>', bodyEnd = '</body>';
                let html = reader.result, index = -1;
                index = html.indexOf(bodyStart);
                if (index >= 0) {
                    html = html.substring(index + bodyStart.length);
                }
                index = html.lastIndexOf(bodyEnd);
                if (index >= 0) {
                    html = html.substring(0, index);
                }
                ue.execCommand('inserthtml', html, false);
            }
        });
}

function dbClick(e, nodeInfo) {
    ue.execCommand('inserthtml', getInsertHtml(nodeInfo), false);
}

function getInsertHtml(nodeInfo, noTip) {
    const variable = getInsertVariable(nodeInfo);
    let insertHtml = '';
    if (!nodeInfo.config.name === templateTree.rootName) {
        insertHtml = `<div data-path="${nodeInfo.config.name}"></div>`
    } else if (nodeInfo.config.isArray) {
        insertHtml = getInsertArrayHtml(nodeInfo, `{${nodeInfo.config.description}}`);
    } else {
        const dot = nodeInfo.config.nodeType === 'ARRAY_INDEX' ? '.' : '';
        insertHtml = '<span><span th:text="${' + wrapperEmptyHandle(variable) + '}" data-path="' + nodeInfo.config.name + '">{' + nodeInfo.config.description + '}</span>' + dot + '&nbsp;</span>';
    }
    return getInsertParentHtml(nodeInfo, insertHtml);
}

function getInsertArrayHtml(nodeInfo, innerText) {
    const currentInfo = getCurrentUEInfo();
    const variable = getInsertVariable(nodeInfo);
    let insertHtml = '';
    if (!currentInfo.inTable) {
        const colorClass = getColorClass();
        insertHtml = `<div class="template-placeholder ${colorClass}" th:each="item : \${${variable}}" data-path="${nodeInfo.config.name}">${innerText}</div>`;
    } else {
        //表格中添加
        const selectItem = ue.selection.getStart();
        const tr = domUtils.findParentByTagName(selectItem, ['tr'], true);
        const titleTr = document.createElement('tr');
        const valueTr = document.createElement('tr');
        domUtils.setAttributes(valueTr, {'th:each':`item: \${${variable}}`});
        domUtils.setAttributes(valueTr, {'data-path':`${nodeInfo.config.name}`});
        const rowSpan = document.createElement('td');
        domUtils.setAttributes(rowSpan, {'th:rowspan': `\${(${variable} == null ? 1 : #lists.size(${variable})) + 1}`, 'rowspan': '2'});
        rowSpan.innerText = nodeInfo.config.description;
        titleTr.appendChild(rowSpan);
        const childNodes = nodeInfo.config.childNodes;
        for (let i = 0; i < childNodes.length; i++) {
            if (childNodes[i].childNodes) {
                continue;
            }
            const titleTd = document.createElement('td');
            const valueTd = document.createElement('td');
            titleTd.innerText = childNodes[i].description;
            let fullPath = nodeInfo.path.split('-');
            fullPath.push(childNodes[i].name);
            let variable = getThymeleafVariable(fullPath);
            valueTd.innerHTML = '<span th:text="${' + wrapperEmptyHandle(variable) +
                '}" data-path="' + childNodes[i].name + '">{' + childNodes[i].description + '}</span>';
            titleTr.appendChild(titleTd);
            valueTr.appendChild(valueTd);
        }

        domUtils.insertAfter(tr, titleTr);
        domUtils.insertAfter(titleTr, valueTr);
    }
    return insertHtml;
}
function wrapperEmptyHandle(target) {
    return `${target} == null ? '' : ${target}`;
}

function getInsertParentHtml(nodeInfo, selfHtml) {
    const parentNode = templateTree.getParentNode(nodeInfo);
    if (parentNode == null) {
        return selfHtml;
    }
    const selectItem = ue.selection.getStart();
    const parent = domUtils.findParent(selectItem, function(node){
        return !!node.dataset && node.dataset['path'] === parentNode.config.name;
    }, true);
    if (parent == null) {
        let insertHtml = '';
        selfHtml = selfHtml || '';
        if (parentNode.config.isArray) {
            insertHtml = getInsertArrayHtml(parentNode, `<br>${selfHtml}<br>`);
        } else {
            insertHtml = `<div data-path="${parentNode.config.name}"><div>${selfHtml}</div></div>`;
        }
        return getInsertParentHtml(parentNode, insertHtml);
    }
    return selfHtml;
}

/**
 * 获取当前输入位置的信息
 * @returns {{path: [], inTable: boolean}}
 */
function getCurrentUEInfo() {
    const info = {
        inTable: false,//是否在table中
        path: [],//路径
    };
    const selectItem = ue.selection.getStart();
    info.inTable = !!domUtils.findParentByTagName(selectItem, ["table"], true);
    const parents = domUtils.findParents(selectItem, true, function (node) {
        return !!node.dataset && !!node.dataset['path'];
    });
    for (let i = 0; i < parents.length; i++) {
        info.path.push(parents[i].dataset['path']);
    }
    const nodeInfo = templateTree.getNode(info.path);
    if (info.path == null || info.path.length === 0) {
        info.path = [templateTree.rootName];
    }
    if (info.path && info.path.length > 0) {
        if (info.path[0] != templateTree.rootName) {
            info.path.unshift(templateTree.rootName);
        }
    }
    return info;
}

/**
 * thymeleaf变量使用规则，array下面会有一个index属性，使用的.count，array循环统一使用变量item
 * @param pathList
 * @returns {string|*}
 */
function getThymeleafVariable(pathList) {
    var variable = '';
    if (pathList == null || pathList.length === 0) {
        return variable;
    }
    const currentNodeInfo = templateTree.getNode(pathList);
    if (pathList.length === 1) {
        return currentNodeInfo.path;
    }
    const parentPaths = pathList.slice(0, pathList.length - 1);
    for (let i = parentPaths.length - 1; i >= 0; i--) {
        const nodeInfo = templateTree.getNode(parentPaths.slice(0, i + 1));
        if (nodeInfo.config && nodeInfo.config.isArray) {
            variable = 'item.';
            break;
        } else {
            variable = nodeInfo.path + '.' + variable;
        }
    }
    if (currentNodeInfo.config.nodeType === 'ARRAY_INDEX') {
        variable = variable.replace(/(item\.)$/, '') + 'itemStat.count';
    } else {
        variable += currentNodeInfo.config.name;
    }
    return variable.replace(/^\./, '').replace(/(\.)$/, '');
}

function getInsertVariable(insertNode) {
    return getThymeleafVariable(insertNode.path.split('-'));
}

function getColorClass() {
    const index = colorIndex;
    colorIndex = (colorIndex + 1) % 4;
    return 'color' + index;
}

ue.addListener('ready', () => {
    window.templateTree = createTemplateTree({
        root: 'swagger',
        type: "SWAGGER",
        container: '.template-container-tree',
        dbClick: dbClick,
        initCallBack: function () {
            const drafts = ue.execCommand('getlocaldata');
            if (drafts) {
                ue.execCommand('drafts');
            } else {
                initDefaultTemplate();
            }
            addGenerateButton();
            ue.addListener('selectionchange', function () {
                let info = getCurrentUEInfo();
                templateTree.focusNode(info.path, true);
            });
        }
    });
});
