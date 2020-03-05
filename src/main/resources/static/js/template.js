import {$, throwError} from "./utils.js";
import {createTemplateTree} from "./template.tree.js";
import EDITOR_CONFIG from './editor.config.js'

const ue = UE.getEditor('editor', EDITOR_CONFIG);

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
    fetch('/template/swagger-default-template.html', {
        method: 'GET'
    })
        .then((res) => res.blob())
        .then((blob) => {
            const reader = new FileReader();
            reader.readAsText(blob, 'UTF-8');
            reader.onloadend = () => {
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
}

ue.addListener('ready', () => {
    window.templateTree = createTemplateTree({
        root: 'swagger',
        type: "SWAGGER",
        container: '.template-container-tree',
        dbClick: dbClick
    });
    if (window.templateTree) {
        const drafts = ue.execCommand('getlocaldata');
        if (drafts) {
            ue.execCommand('drafts');
        } else {
            // initDefaultTemplate();
        }
        addGenerateButton();
    }
});
