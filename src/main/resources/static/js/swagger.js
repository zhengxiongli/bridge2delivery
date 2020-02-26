const fileInput = $('input[type="file"]');
const JSON_FILE_TYPE = 'application/json';

function $(selector) {
    return document.querySelector(selector)
}

function throwError(msg) {
    alert(`[ERROR]: ${msg}`);
    throw new Error(msg)
}

function proxyFileSelect() {
    const selectFileBtn = $('.select-file-button');
    selectFileBtn.addEventListener('click', () => {
        fileInput.click()
    })
}

function registerFileSelectListener() {
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        validateJSON(file);
        renderJSON(file)
    })
}

function isValidJSON(fileType) {
    return !(!fileType || fileType !== JSON_FILE_TYPE);
}

function parseJSON(data) {
    try {
        JSON.parse(data);
    } catch (err) {
        console.log('parseJSON:', err.message);
        throwError('解析 JSON 文件失败')
    }
}

function validateJSON(file) {
    if (!isValidJSON(file.type)) {
        throwError('请选择正确的json文件')
    }
    return true
}

function downloadFile(blob) {
    const filename = `test.doc`;
    const a = document.createElement('a');
    const url = URL.createObjectURL(blob);
    a.href = url;
    a.download = filename;
    a.click();
    URL.revokeObjectURL(url);
}

function uploadJSON(file) {
    const fd = new FormData();
    fd.append('swaggerFile', file);
    fetch('/swagger/upload', {
        method: 'POST',
        body: fd,
    }).then(() => {
        fetch('/swagger/doc', {
            method: 'GET',
        })
            .then(res => res.blob())
            .then(downloadFile)
    })
        .catch(() => alert('下载失败'))
}

function renderJSON(file) {
    const reader = new FileReader();
    reader.onprogress = () => {
        console.log('loading..')
    };
    reader.onerror = () => {
        alert('[ERROR]:读取JSON文件失败!')
    };
    reader.onabort = () => {
        alert('[ABORT]:读取JSON文件失败!')
    };
    reader.onload = () => {
        parseJSON(reader.result);
        uploadJSON(file)
    };
    reader.readAsText(file, 'utf-8')
}

function addDragAreaHighlight(dragArea) {
    dragArea.classList.add('drag-area-highlight')
}

function removeDragAreaHighlight(dragArea) {
    dragArea.classList.remove('drag-area-highlight')
}

function registerDrag(dragArea) {
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

window.onload = () => {
    proxyFileSelect();
    registerDrag($('.swagger-container'));
    registerFileSelectListener()
};