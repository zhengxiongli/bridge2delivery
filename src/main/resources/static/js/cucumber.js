import {$, getReader, validateResponse, validateTemplate, validateTemplateMeta, throwError} from "./utils.js";
import {initDrag} from "./drag/drag.js";

const fileInput = $('input[name="select-file-input"]');
const templateInput = $('input[name="select-template-input"]');
const featureTemplateInfo = `<div class="feature-item @{disabledClass}" data-index="@{dataIndex}">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" @{checked} @{disabled}>
                                            <span></span>
                                        </label>
                                    </div>
                                    <div class="file-info">
                                        <img src="@{image}">
                                        <div class="file-name">@{name}</div>
                                        <div class="file-path">@{path}</div>
                                    </div>
                                </div>`;
let featureFiles = [], unrecognizedFeatureFiles;
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

function showPreviewIFrame() {
    const preview = $('.preview'), indexes = getPathIndexs();
    if (indexes.length == 0) {
        throwError('请选择文件');
    }
    const previewIframe = $('.preview-iframe');
    preview.style.display = "block";
    previewIframe.src = `/cucumber/html?indexes=${indexes}&${Date.now()}`;
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
    let hasFeature = false;
    for (let i = 0; i < files.length; i++) {
        if (/(.feature)$/ig.test(files[i].name)) {
            fd.append('file', files[i]);
            hasFeature = true;
        }
    }
    if (!hasFeature) {
        alert('无任何.feature文件');
        return;
    }
    fetch('/cucumber/feature', {
        method: 'POST',
        body: fd,
    }).then(validateResponse)
        .then(res => res.json())
        .then(res => {
            featureFiles = res.data.featureFiles || [];
            unrecognizedFeatureFiles = res.data.unrecognizedFeatureFiles || [];
            showUploadResult();
        });
}

function showUploadResult(resetorder) {
    const result = $('#scan-result'), resultItems = $('#feature-items'),
        resultMsg = $('#result-message'), checkStatus = getCheckStatus();
    let message = '', innerHTML = '';
    if (featureFiles.length == 0) {
        message = `所有${unrecognizedFeatureFiles.length}个.feature文件不识别`;
    } else if (unrecognizedFeatureFiles.length == 0) {
        message = `共成功识别到${featureFiles.length}个.feature文件`;
    } else {
        message = `共成功识别到${featureFiles.length}个.feature文件，另有${unrecognizedFeatureFiles.length}个.feature文件不识别`;
    }
    resultMsg.innerText = message;
    result.style.display = 'block';
    for (let i = 0; i < featureFiles.length; i++) {
        const fileInfo = getFilePathAndName(featureFiles[i]);
        innerHTML += featureTemplateInfo.replace('@{disabledClass}', '')
            .replace('@{dataIndex}', i)
            .replace('@{checked}', !resetorder ? 'checked' : (checkStatus[i] ? 'checked' : ''))
            .replace('@{disabled}', '')
            .replace('@{image}', '/assets/cucumber-file.svg')
            .replace('@{name}', fileInfo.name)
            .replace('@{path}', fileInfo.path);
    }

    for (let i = 0; i < unrecognizedFeatureFiles.length; i++) {
        const fileInfo = getFilePathAndName(unrecognizedFeatureFiles[i]);
        innerHTML += featureTemplateInfo.replace('@{disabledClass}', 'disabled')
            .replace('@{dataIndex}', i)
            .replace('@{checked}', '')
            .replace('@{disabled}', 'disabled')
            .replace('@{image}', '/assets/unknown-file.svg')
            .replace('@{name}', fileInfo.name)
            .replace('@{path}', fileInfo.path);
    }
    if (resetorder) {
        let allChecked = true;
        for (let i = 0; i < checkStatus.length; i++) {
            if (!checkStatus[i]) {
                allChecked = false;
                break;
            }
        }
        $('#check-all-features').checked = allChecked;
    } else {
        $('#check-all-features').checked = true;
    }

    resultItems.innerHTML = innerHTML;
    addItemCheckListener();
    if (featureFiles.length > 0) {
        showPreviewIFrame();
    }

    function getFilePathAndName(file) {
        const fileInfo = {}, index = file.lastIndexOf('/');
        fileInfo.name = file.substring(index + 1);
        fileInfo.path = index == -1 ? './' : './' + file.substring(0, index + 1);
        return fileInfo;
    }

    function getCheckStatus() {
        if (!resultItems.children || resultItems.children.length == 0) {
            return [];
        }
        const items = resultItems.children, enableItems = [];
        for (let i = 0; i < items.length; i++) {
            if (items[i].className.indexOf('disabled') < 0) {
                enableItems.push(items[i]);
            }
        }
        const checkStatus = new Array(enableItems.length);
        for (let i = 0; i < enableItems.length; i++) {
            const index = parseInt(enableItems[i].dataset['index']);
            checkStatus[index] = enableItems[i].querySelector('input[type="checkbox"]').checked;
        }
        return checkStatus;
    }
    if (!resetorder) {
        collspandResult();
    }
}

function addItemCheckListener() {
    const checkAll = $('#check-all-features'),
    items = document.querySelectorAll('#feature-items input[type="checkbox"]');
    for (var i = 0; i < items.length; i++) {
        items[i].addEventListener('change', function() {
            let isAllSelected = true;
            for (let i = 0; i < items.length; i++) {
                if (!items[i].disabled && !items[i].checked) {
                    isAllSelected = false;
                    break;
                }
            }
            checkAll.checked = isAllSelected;
        });
    }
}

function getPathIndexs() {
    const indexes = [], resultItems = $('#feature-items').children;
    for (let i = 0; i < resultItems.length; i++) {
        if (resultItems[i].querySelector('input[type="checkbox"]').checked) {
            indexes.push(resultItems[i].dataset['index']);
        }
    }
    return indexes;
}

function expandResult() {
    const result = $('#expand-result');
    if (result.className.indexOf('expand') > 0) {
        return;
    }
    result.className = result.className.replace(/collspand/ig, '').trim() + ' expand';
    $('#feature-info').style.display = '';
}

function collspandResult() {
    const result = $('#expand-result');
    if (result.className.indexOf('collspand') >= 0) {
        return;
    }
    result.className = result.className.replace(/expand/ig, '').trim() + ' collspand';
    $('#feature-info').style.display = 'none';
}

function initScanResultModel() {
    const resultItems = $('#feature-items').children;
    function changeAllCheck(checked) {
        const items = document.querySelectorAll('#feature-items input[type="checkbox"]');
        for (let i = 0; i < items.length; i++) {
            if (items[i].disabled) {
                continue;
            }
            items[i].checked = checked;
        }
    }

    $('#update-preview').addEventListener('click', function() {
        showPreviewIFrame();
    });

    $('#reset-order').addEventListener('click', function() {
        showUploadResult(true);
    });

    $('#check-all-features').addEventListener('change', function() {
        changeAllCheck(this.checked);
    });

    $('#expand-result').addEventListener('click', function() {
       if (this.className.indexOf('expand') >= 0) {
           collspandResult();
       } else {
           expandResult();
       }
    });

    $('#download-excel').addEventListener('click', function() {
       const downloadElm = document.createElement('a'), indexes = getPathIndexs();
        if (indexes.length == 0) {
            throwError('请选择文件');
        }
       downloadElm.href = '/cucumber/excel?indexes=' + indexes;
       downloadElm.click();
       downloadElm.remove();
    });
}

function registerGlobalErrorHandler() {
    window.addEventListener('unhandledrejection', (e) => {
        console.error('[unhandledrejection]', e.reason);
        alert(e.reason.message || '系统异常')
    })
}

function reloadTemplate(file) {
    const reader = getReader('模版', () => {
        validateTemplateMeta(reader.result, 'CUCUMBER');
    });
    reader.readAsText(file, 'utf-8');

    const fd = new FormData();
    fd.append('file', file);
    fetch('/cucumber/template', {
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
    onResetDefaultTemplate();
    initScanResultModel();
    initDrag({container: '#feature-items',
        filterItem: function(ele) {
            return ele.className && ele.className.indexOf('disabled') >= 0;
        }}).start();
};