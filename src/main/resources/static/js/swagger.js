const fileInput = $('input[type="file"]')
const JSON_FILE_TYPE = 'application/json'

function $(selector){
    return document.querySelector(selector)
}

function throwError(msg) {
    alert(`[ERROR]: ${msg}`)
    throw new Error(msg)
}

function proxyFileSelect() {
    const selectFileBtn = $('.select-file-button')
    selectFileBtn.addEventListener('click',() => {
        fileInput.click()
    })
}

function registerFileSelectListener( ) {
    fileInput.addEventListener('change',() => {
        const file = fileInput.files[0]
        validateJSON(file)
        renderJSON(file)
    })
}

function isValidJSON(fileType) {
    if (!fileType || fileType !== JSON_FILE_TYPE) {
        return false
    }
    return true
}

function parseJSON(data) {
    try {
        const json = JSON.parse(data)
        console.log(json)
    } catch (err) {
        console.log('parseJSON:', err.message)
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
    const a = document.createElement('a');
    a.href = "/swagger/word";
    a.click();
    a.remove();
}

function registerParseJsonUrl() {
    const analysisBtn = $(".analysis");
    analysisBtn.addEventListener('click', () => {
        const urlInput = $('.url-input'), fd = new FormData();
        fd.append("url", urlInput.value);
        fetch('/swagger/url', {
            method: "POST",
            body: fd
        })
            .then(res => res.blob())
            .then(showPreview)
            .catch((res) => alert(res.data.message));
    });
}

function showPreview() {
    const preview = $('.preview'), previewImg = $('.preview-img');
    preview.style.display = "block";
    preview
    previewImg.src = '/swagger/image?' + (new Date().getTime());
}

function uploadJSON(file) {
    const fd = new FormData()
    fd.append('swaggerFile', file)
    fetch('/swagger/json', {
        method: 'POST',
        body: fd,
    })
        .then(res => {/*res.blob()*/})
        .then(showPreview)
        .catch(() => alert('上传失败'))
}

function renderJSON(file) {
    const reader = new FileReader()
    reader.onprogress = ()=>{
        console.log('loading..')
    }
    reader.onerror = () => {
        alert('[ERROR]:读取JSON文件失败!')
    }
    reader.onabort = () => {
        alert('[ABORT]:读取JSON文件失败!')
    }
    reader.onload = () => {
        parseJSON(reader.result)
        uploadJSON(file)
    }
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
            e.preventDefault()
            e.stopPropagation()
            addDragAreaHighlight(dragArea)
        },
        false
    )
    dragArea.addEventListener(
        'dragleave',
        (e) => {
            e.preventDefault()
            e.stopPropagation()
            removeDragAreaHighlight(dragArea)
        },
        false
    )
    dragArea.addEventListener(
        'dragover',
        (e) => {
            e.preventDefault()
            e.stopPropagation()
        },
        false
    )

    dragArea.addEventListener(
        'drop',
        (e) => {
            e.preventDefault()
            e.stopPropagation()
            const file = e.dataTransfer.files[0]
            removeDragAreaHighlight(dragArea)
            validateJSON(file)
            renderJSON(file)
        },
        false
    )
}

window.onload = () => {
    proxyFileSelect()
    registerDrag($('.swagger-container'))
    registerFileSelectListener()
    registerParseJsonUrl();
}