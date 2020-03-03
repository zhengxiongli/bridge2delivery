import {$} from "./utils.js";
import EDITOR_CONFIG from './editor.config.js'

const ue = UE.getEditor('editor', EDITOR_CONFIG);

function addGenerateButton() {
    const wrapper = document.createElement('div')
    wrapper.className = 'template-generate-btn-wrapper'
    const button = document.createElement('button')
    button.innerText = '生成'
    button.className = 'template-generate-btn'
    wrapper.append(button)
    $('.edui-editor-toolbarboxinner').append(wrapper)
}


ue.addListener('ready', (editor) => {
    addGenerateButton()
    ue.execCommand('drafts');
});
