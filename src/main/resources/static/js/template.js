import {$, getSessionData, META_KEY, removeSessionData, getUrlParam} from "./utils.js";
import {createTemplateTree} from "./template.tree.js";
import EDITOR_CONFIG from './editor.config.js'
import {newModal} from "./modal/modal.js"

let ue = null;
const domUtils = UE.dom.domUtils, notAutoShowInstruction = 'notAutoShowInstruction';
let colorIndex = 1, timeOutId;

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

function formatDateAndMonth(number) {
    const value = '00' + number;
    return value.substring(value.length - 2, value.length);
}

function generateTemplate() {
    const date = new Date();
    ue.removeClassFile('/js/ueditor/themes/iframe.css');
    ue.removeClassFile('/js/ueditor/themes/iframe-preview.css');
    ue.addMeta(META_KEY, templateTree.type);
    const html = ue.getAllHtml();
    ue.loadClassFile('/js/ueditor/themes/iframe.css');
    if (!isExcelTemplate()) {
        ue.loadClassFile('/js/ueditor/themes/iframe-preview.css');
    }
    const filename = `${templateTree.data.filePrefix}${date.getFullYear()}${formatDateAndMonth(date.getMonth() + 1)}${formatDateAndMonth(date.getDate())}.html`;
    const a = document.createElement('a');
    const url = URL.createObjectURL(new Blob([html], {type: 'text/html'}));
    a.href = url;
    a.download = filename;
    a.click();
    URL.revokeObjectURL(url);
}

function initDefaultTemplate() {
    fetch(templateTree.data.defaultUrl, {
        method: 'GET'
    })
        .then((res) => res.blob())
        .then((blob) => {
            const reader = new FileReader();
            reader.readAsText(blob, 'UTF-8');
            reader.onloadend = () => insertBodyFromHtml(reader.result);
        });
}

export function insertBodyFromHtml(html) {
    const bodyStart = '<body>', bodyEnd = '</body>';
    let index = html.indexOf(bodyStart);
    if (index >= 0) {
        html = html.substring(index + bodyStart.length);
    }
    index = html.lastIndexOf(bodyEnd);
    if (index >= 0) {
        html = html.substring(0, index);
    }
    const afterFilterJsCode = html.split(new RegExp('<script[^>]*?>[\\s\\S]*?<\\/script>')).join('');
    ue.execCommand('inserthtml', afterFilterJsCode, false);
}

function dbClick(e, nodeInfo) {
    if (isExcelTemplate()) {
        const tr = getInsertTr(ue.selection.getStart());
        if (!tr) {
            alert('请在表格中添加');
            return;
        }
        let parentNode = templateTree.getParentNode(nodeInfo);
        while (parentNode && !parentNode.config.isArray) {
            parentNode = templateTree.getParentNode(parentNode);
        }
        if (parentNode == null) {
            alert('Excel配置错误');
            return;
        }
        domUtils.setAttributes(tr, {'th:each': `item: \${${getInsertVariable(parentNode)}}`});
        domUtils.setAttributes(tr, {'data-path': `${parentNode.config.name}`});
    }
    const insertHtml = getInsertHtml(nodeInfo);
    ue.execCommand('inserthtml', insertHtml, false);
}

function getInsertHtml(nodeInfo) {
    const variable = getInsertVariable(nodeInfo);
    let insertHtml;
    if (!nodeInfo.config.name === templateTree.data.rootName) {
        insertHtml = `<div data-path="${nodeInfo.config.name}"></div>`
    } else if (nodeInfo.config.isArray) {
        insertHtml = getInsertArrayHtml(nodeInfo, `{${nodeInfo.config.description}}`);
    } else {
        const dot = nodeInfo.config.nodeType === 'ARRAY_INDEX' && !isExcelTemplate() ? '.' : '';
        let path = nodeInfo.config.name;
        if (isExcelTemplate() && variable.split('.').length > 2) {
            path = variable.split('.').slice(1).join('-');
        }
        insertHtml = '<span><span th:text="${' + wrapperEmptyHandle(variable) + '}" data-path="' + path + '">{' + nodeInfo.config.description + '}</span>' + dot + '&nbsp;</span>';
    }
    return getInsertParentHtml(nodeInfo, insertHtml);
}

function getInsertArrayHtml(nodeInfo, innerText) {
    const currentInfo = getCurrentUEInfo();
    const variable = getInsertVariable(nodeInfo);
    let insertHtml = '';
    if (!currentInfo.inTable) {
        const colorClass = getColorClass();
        insertHtml = `<div class="template-placeholder ${colorClass}" th:each="item : \${${variable}}" data-path="${nodeInfo.config.name}"><p>${innerText}</p></div>`;
    } else {
        //表格中添加
        const selectItem = ue.selection.getStart();
        const tr = getInsertTr(selectItem);
        const titleTr = document.createElement('tr');
        const valueTr = document.createElement('tr');
        domUtils.setAttributes(valueTr, {'th:each': `item: \${${variable}}`});
        domUtils.setAttributes(valueTr, {'data-path': `${nodeInfo.config.name}`});
        const rowSpan = document.createElement('td');
        domUtils.setAttributes(rowSpan, {
            'th:rowspan': `\${(${variable} == null ? 1 : #lists.size(${variable})) + 1}`,
            'rowspan': '2'
        });
        rowSpan.innerText = nodeInfo.config.description;
        titleTr.appendChild(rowSpan);
        const childNodes = nodeInfo.config.childNodes;
        for (let i = 0; i < childNodes.length; i++) {
            if (childNodes[i].childNodes || childNodes[i].nodeType === 'ARRAY_INDEX') {
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

function getInsertTr(selectItem) {
    const selectTr = domUtils.findParentByTagName(selectItem, ['tr'], true),
        rowSpan = selectItem.rowSpan;
    let retTr = selectTr;
    for (let i = 1; i < rowSpan; i++) {
        retTr = domUtils.getNextDomNode(retTr, false);
    }
    return retTr;
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
    const parent = domUtils.findParent(selectItem, function (node) {
        return !!node.dataset && node.dataset['path'] === parentNode.config.name;
    }, true);
    if (parent == null) {
        let insertHtml = selfHtml || '';
        if (parentNode.config.isArray) {
            insertHtml = getInsertArrayHtml(parentNode, `<br>${selfHtml}<br>`);
        } else if (templateTree.data.fileType !== 'EXCEL'){
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
    info.table = domUtils.findParentByTagName(selectItem, ["table"], true);
    info.inTable = !!info.table;

    const parents = domUtils.findParents(selectItem, true, function (node) {
        return !!node.dataset && !!node.dataset['path'];
    });
    for (let i = 0; i < parents.length; i++) {
        info.path.push(parents[i].dataset['path']);
    }
    if (info.path == null || info.path.length === 0) {
        info.path = [templateTree.data.rootName];
    }
    if (info.path && info.path.length > 0) {
        if (info.path[0] !== templateTree.data.rootName) {
            info.path.unshift(templateTree.data.rootName);
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
    let variable = '';
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
            variable = 'item.' + variable;
            break;
        } else {
            variable = nodeInfo.config.name + '.' + variable;
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

function appendPToBody() {
    clearTimeout(timeOutId);
    timeOutId = setTimeout(function(){
        const lastChild = ue.body.lastChild;
        if (!lastChild || lastChild.tagName !== 'P') {
            const node = ue.document.createElement("p");
            node.innerHTML = '&#8203;';
            ue.body.append(node)
        } else if (!lastChild.innerHTML) {
            lastChild.innerHTML = '&#8203;';
        }
    }, 500)
}

function autoShowInstruction() {
    if (localStorage.getItem(notAutoShowInstruction)) {
        return;
    }
    showInstruction();
}

function showInstruction() {
    const modal = newModal({
        title: '如何使用',
        contentWidth: '368px',
        buttons: [{
            text: '不再自动弹出',
            type: 'check',
            align: 'left',
            id: 'auto-show'
        }, {
            text: '确定',
            type: 'button',
            align: 'right',
            onClick: function() {
                if ($('#auto-show').checked) {
                    localStorage.setItem(notAutoShowInstruction, true);
                } else {
                    localStorage.removeItem(notAutoShowInstruction);
                }
                modal.close();
            }
        }],
        content: `1. 在右侧富文本编辑器内，可任意输入文本<br>
                  2. 双击左侧菜单条目，可在右侧编辑器内插入占位符<br>
                  3. 有[array]标志的占位符，在编辑模板时会显示方框，并在使用模板生成内容时产生多个内容（<a href="/assets/instruction.pdf" target="_blank">查看示例</a>）<br>
                  4. 建议将表格插入在PathList层级内，并保留表格后的换行<br>
                  5. 使用“切换HTML代码”按钮，可以直接编辑模板源码<br>`,
        beforeShow: function() {
            $('#auto-show').checked = !!localStorage.getItem(notAutoShowInstruction);
        }
    });
    modal.show();
}

function isExcelTemplate() {
    return templateTree.data.fileType === 'EXCEL';
}

function initUEEditor() {
    ue.removeItems(['style#tablesort', 'style#list', 'style#pagebreak', 'style#pre', 'style#loading', 'style#anchor']);
    if (!isExcelTemplate()) {
        ue.loadClassFile('/js/ueditor/themes/iframe-preview.css');
    }
    const uploadTemplate = getSessionData('uploadTemplate');
    if (uploadTemplate) {
        insertBodyFromHtml(uploadTemplate);
        removeSessionData('uploadTemplate');
    } else if (ue.execCommand('getlocaldata')) {
        ue.execCommand('drafts');
    } else {
        initDefaultTemplate();
    }
    addGenerateButton();
}

function addUEEditorListener() {
    ue.addListener('selectionchange', function () {
        let info = getCurrentUEInfo();
        if (isExcelTemplate()) {
            const trNodes = domUtils.findChildrenByTagName(info.table, 'tr', function(node) {
                return !!node.getAttribute('th:each');
            }), node = templateTree.getNode(info.path);
            const trNode = trNodes.length > 0 ? trNodes[0] : null;
            if (!!trNode && domUtils.findParentByTagName(ue.selection.getStart(), 'tr') != trNode
                || (node && !node.isRoot && (!node.config.childNodes || node.config.childNodes.length == 0))) {
                templateTree.disableAll();
                return;
            } else {
                templateTree.enableAll(isExcelTemplate());
            }
        }
        templateTree.focusNode(info.path, !isExcelTemplate());
    });
    ue.addListener('contentChange', function() {
        appendPToBody();
    });
}

function intervalAppendP() {
    setTimeout(function() {
        appendPToBody();
        intervalAppendP();
    }, 2000);
}

function init() {
    const type = getUrlParam('type') || 'SWAGGER';
    window.templateTree = createTemplateTree({
        type: type,
        container: '.template-container-tree',
        dbClick: dbClick,
        initCallBack: function (tree) {
            const ueConfig = EDITOR_CONFIG;
            if (tree.data.fileType === 'EXCEL') {
                const toolbars = [];
                for (let i = 0; i < ueConfig.toolbars[0].length; i++) {
                    if (ueConfig.toolbars[0][i] === 'paragraph') {
                        ueConfig.toolbars[0].splice(i, 2);
                        break;
                    }
                }
            }
            ue = UE.getEditor('editor', ueConfig);
            ue.addListener('ready', () => {
                initUEEditor();
                addUEEditorListener();
                intervalAppendP();
                $('.template-instruction a').addEventListener('click', function() {
                    showInstruction();
                });
                autoShowInstruction();
            });
        }
    });
}

init();


