import {$, throwError} from "./utils.js";
import {createTemplateTree} from "./template.tree.js";
import EDITOR_CONFIG from './editor.config.js'

const ue = UE.getEditor('editor', EDITOR_CONFIG);
const domUtils = UE.dom.domUtils;

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

function generateTemplate() {
    const html = ue.getAllHtml();
    const filename = `swagger-template.html`;
    const a = document.createElement('a');
    const url = URL.createObjectURL(new Blob([html], {type: 'text/html'}));
    a.href = url;
    a.download = filename;
    a.click();
    URL.revokeObjectURL(url);
}

function initDefaultTemplate() {
    fetch('/template/swagger.html', {
        method: 'GET'
    })
        .then((res) => res.blob())
        .then((blob) => {
            const reader = new FileReader();
            reader.readAsText(blob, 'UTF-8');
            reader.onloadend = () => {
                debugger;
                ue.setContent(reader.result)
            }
        });
}

function dbClick(e, nodeInfo) {
    console.log(e, nodeInfo);
    const node = new UE.uNode({
        type: 'element',
        tagName: 'span',
        attrs: {'th:text': '${swaggerInfo.' + nodeInfo.config.name + '}'}
    });
    node.innerText(`{` + nodeInfo.config.description + `}`);
    console.log(ue.selection.getRange())
    ue.execCommand('inserthtml', node.toHtml(false));
    console.log(ue.selection.getRange().startContainer)
    getCurrentUEInfo();
}

/**
 * 获取当前输入位置的信息
 * @returns {{path: [], variableName: string, inTable: boolean}}
 */
function getCurrentUEInfo() {
    const info = {
        inTable: false,//是否在table中
        path: [],//路径
        variableName: ''//thymeleaf需要使用名字
    };
    const selectItem = ue.selection.getStart();
    info.inTable = !!domUtils.findParentByTagName(selectItem, ["table"], true);
    const parents = domUtils.findParents(selectItem, true, function(node){
        return !!node.dataset['path'];
    });
    for (let i = 0; i < parents.length; i++) {
        info.path.push(parents[i].dataset['path']);
    }
    const variable = getThymeleafVariable(info.path.slice(0, info.path.length - 1));
    const nodeInfo = templateTree.getNode(info.path);
    info.variableName = getThymeleafVariable(info.path);
    if (info.path == null || info.path.length == 0) {
        info.path = [templateTree.rootName];
    }
    if (!info.variableName) {
        info.variableName = templateTree.rootName;
    }
    return info;
}

/**
 * thymeleaf变量使用规则，array下面会有一个index属性，使用的.count，array循环统一使用变量item
 * @param pathList
 * @returns {string|*}
 */
function getThymeleafVariable(pathList) {
    var variable = templateTree.previewUrl;
    if (pathList == null || pathList.length == 0) {
        return variable;
    }
    const currentNodeInfo = templateTree.getNode(pathList);
    if (pathList.length == 1) {
        return currentNodeInfo.path;
    }
    const parentPaths = pathList.slice(0, pathList.length - 1);
    for (let i = parentPaths.length - 1; i >= 0; i--) {
        const nodeInfo = templateTree.getNode(parentPaths.slice(0, i + 1));
        if (nodeInfo.config.isArray) {
            variable = 'item.' + variable;
            break;
        } else {
            variable = nodeInfo.path + '.' + variable;
        }
    }
    if (currentNodeInfo.nodeType === 'ARRAY_INDEX') {
        variable += 'count';
    } else {
        variable += currentNodeInfo.name;
    }
    return variable;
}

ue.addListener('ready', () => {
    window.templateTree = createTemplateTree({
        root: 'swagger',
        type: "SWAGGER",
        container: '.template-container-tree',
        dbClick: dbClick,
        initCallBack: function() {
            const drafts = ue.execCommand('getlocaldata');
            if (drafts) {
                ue.execCommand('drafts');
            } else {
                initDefaultTemplate();
            }
            addGenerateButton();
            ue.addListener('selectionchange', function() {
                let info = getCurrentUEInfo();
                templateTree.focusNode(info.path);
            });
        }
    });
});
