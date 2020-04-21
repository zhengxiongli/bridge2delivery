import {
    $,
    getReader,
    isURL,
    parseJSON,
    validateJSON,
    validateResponse,
    validateTemplate,
    validateTemplateMeta
} from "./utils.js";

const fileInput = $('input[name="select-file-input"]');
const templateInput = $('input[name="select-template-input"]');

function onRestDefaultTemplate() {
    const defaultTmp = $('.reset-to-default-btn');
    if (!defaultTmp) return;
    defaultTmp.addEventListener('click', () => {
        fetch('/swagger/default/template', {
            method: "PUT",
        })
            .then(() => alert('重置成功!')).then(showPreviewIFrame);
    })
}

function proxyFileSelect() {
    const selectFileBtn = $('.select-file-button');
    if (!selectFileBtn) return;
    selectFileBtn.addEventListener('click', () => {
        fileInput.click()
    })
}

function registerFileSelectListener() {
    if (!fileInput) return;
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        validateJSON(file);
        renderJSON(file);
        fileInput.value = '';
    })
}

function registerParseJsonUrl() {
    const analysisBtn = $(".analysis");
    if (!analysisBtn) return;
    analysisBtn.addEventListener('click', () => {
        const urlInput = $('.url-input');
        if (!isURL(urlInput.value)) {
            return alert('请输入有效的URL')
        }
        const fd = new FormData();
        fd.append("url", urlInput.value);
        fetch('/swagger/url', {
            method: "POST",
            body: fd
        })
            .then(validateResponse)
            .then(showPreviewIFrame)
    });
}

function showPreviewIFrame() {
    const preview = $('.preview');
    const previewIframe = $('.preview-iframe');
    preview.style.display = "block";
    previewIframe.src = `/swagger/html?${Date.now()}`;
}

function uploadJSON(file) {
    const fd = new FormData();
    fd.append('swaggerFile', file);
    fetch('/swagger/json', {
        method: 'POST',
        body: fd,
    }).then(validateResponse)
        .then(showPreviewIFrame)
}

function renderJSON(file) {
    const reader = getReader('JSON', () => {
        parseJSON(reader.result);
        uploadJSON(file)
    });
    reader.readAsText(file, 'utf-8')
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
            const file = e.dataTransfer.files[0];
            removeDragAreaHighlight(dragArea);
            validateJSON(file);
            renderJSON(file)
        },
        false
    )
}

function registerGlobalErrorHandler() {
    window.addEventListener('unhandledrejection', (e) => {
        console.error('[unhandledrejection]', e.reason);
        alert(e.reason.message || '系统异常')
    })
}

function reloadTemplate(file) {
    const reader = getReader('模版', () => {
        validateTemplateMeta(reader.result, 'SWAGGER');
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
    registerParseJsonUrl();
    registerGlobalErrorHandler();
    registerTemplateSelectListener();
    onRestDefaultTemplate()
};