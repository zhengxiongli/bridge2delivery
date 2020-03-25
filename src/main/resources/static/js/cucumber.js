import {$, getReader, validateResponse, validateTemplate, validateTemplateMeta} from "./utils.js";

const fileInput = $('input[name="select-file-input"]');
const templateInput = $('input[name="select-template-input"]');

function onResetDefaultTemplate() {
    const defaultTmp = $('.reset-to-default-btn');
    if (!defaultTmp) return;
    defaultTmp.addEventListener('click', () => {
        fetch('/cucumber/default/template', {
            method: "PUT",
        })
            .then(() => alert('重置成功!')).then(showPreviewIFrame);
    })
}

function proxyFileSelect() {
    const selectFileBtn = $('button.search-container');
    if (!selectFileBtn) return;
    selectFileBtn.addEventListener('click', () => {
        fileInput.click()
    })
}

function registerFileSelectListener() {
    if (!fileInput) return;
    fileInput.addEventListener('change', () => {
        uploadFeatures(fileInput.files);
        fileInput.value = '';
    })
}

function showScanResultIFrame() {
    const scanResult = $('.scan-result');
    const scanResultIframe = $('.scan-result-content-iframe');
    scanResult.style.display = "block";
    scanResultIframe.src = `/cucumber/scan-result?${Date.now()}`;
}

function showPreviewIFrame() {
    const preview = $('.preview');
    const previewIframe = $('.preview-iframe');
    preview.style.display = "block";
    previewIframe.src = `/cucumber/html?${Date.now()}`;
}

function addDragAreaHighlight(dragArea) {
    dragArea.classList.add('drag-area-highlight')
}

function removeDragAreaHighlight(dragArea) {
    dragArea.classList.remove('drag-area-highlight')
}

function registerDrag(dragArea) {
    if (!dragArea) return;
    dragArea.addEventListener(
        'dragenter',
        (e) => {
            e.preventDefault();
            e.stopPropagation();
            addDragAreaHighlight(dragArea)
        },
        false
    );
    dragArea.addEventListener(
        'dragleave',
        (e) => {
            e.preventDefault();
            e.stopPropagation();
            removeDragAreaHighlight(dragArea)
        },
        false
    );
    dragArea.addEventListener(
        'dragover',
        (e) => {
            e.preventDefault();
            e.stopPropagation()
        },
        false
    );

    dragArea.addEventListener(
        'drop',
        (e) => {
            e.preventDefault();
            e.stopPropagation();
            removeDragAreaHighlight(dragArea);
            uploadFeatures(e.dataTransfer.files);
        },
        false
    )
}

function uploadFeatures(files) {
    const fd = new FormData();
    for (let i = 0; i < files.length; i++) {
        if (/(.feature)$/ig.test(files[i].name)) {
            fd.append('file', files[i]);
        }
    }
    fetch('/cucumber/feature', {
        method: 'POST',
        body: fd,
    }).then(validateResponse)
        .then(showScanResultIFrame)
        .then(showPreviewIFrame);
}

function registerGlobalErrorHandler() {
    window.addEventListener('unhandledrejection', (e) => {
        console.error('[unhandledrejection]', e.reason);
        alert(e.reason.message || '系统异常')
    })
}

function reloadTemplate(file) {
    const reader = getReader('模版', () => {
        validateTemplateMeta(reader.result);
    });
    reader.readAsText(file, 'utf-8');

    const fd = new FormData();
    fd.append('swaggerFile', file);
    fetch('/swagger/template', {
        method: 'POST',
        body: fd,
    }).then(validateResponse).then(showPreviewIFrame);
}

function proxyTemplateSelect() {
    const selectTemplateBtn = $('.select-template-btn');
    if (!selectTemplateBtn) return;
    selectTemplateBtn.addEventListener('click', () => {
        templateInput.click();
    })
}

function registerTemplateSelectListener() {
    if (!templateInput) return;
    templateInput.addEventListener('change', () => {
        const file = templateInput.files[0];
        validateTemplate(file);
        reloadTemplate(file);
        templateInput.value = '';
    })
}

window.onload = () => {
    proxyFileSelect();
    proxyTemplateSelect();
    registerDrag($('.swagger-container'));
    registerFileSelectListener();
    registerGlobalErrorHandler();
    registerTemplateSelectListener();
    onResetDefaultTemplate()
};