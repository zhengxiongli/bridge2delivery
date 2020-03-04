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
    console.log(ue.getContent())
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
    alert('dbclick');
    console.log(e, nodeInfo);
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
            initDefaultTemplate();
        }
        addGenerateButton();
    }
});
