import {$, getReader, setSessionData, validateTemplate, validateTemplateMeta, getTemplateType} from "./utils.js";

const fileInput = $('input[name="select-file-input"]');

function proxyFileSelect() {
    const selectFileBtn = $('.load-template-btn');
    if (!selectFileBtn) return;
    selectFileBtn.addEventListener('click', () => {
        fileInput.click()
    });
}

function registerFileSelectListener() {
    if (!fileInput) return;
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        validateTemplate(file);
        fileInput.value = '';
        loadTemplate(file);
    })
}

function loadTemplate(file) {
    const reader = getReader('模版', () => {
        const content = reader.result;
        const templateType = getTemplateType(content);
        setSessionData('uploadTemplate', content);
        window.location.href = `/template?type=${templateType}`;
    });
    reader.readAsText(file, 'utf-8')
}

window.onload = () => {
    proxyFileSelect();
    registerFileSelectListener();
};