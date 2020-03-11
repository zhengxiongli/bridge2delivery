export const JSON_FILE_TYPE = 'application/json';
export const TEMPLATE_FILE_TYPE = 'text/html';
export const TEMPLATE_TYPE = 'SWAGGER';
export const META_KEY = 'template-type';

export function setSessionData(key, value) {
    window.sessionStorage.setItem(key, value);
}

export function getSessionData(key) {
    return window.sessionStorage.getItem(key);
}

export function removeSessionData(key) {
    return window.sessionStorage.removeItem(key);
}

export async function validateResponse(response) {
    if (response.status >= 200 && response.status < 300) {
        return response;
    }
    const {message} = await response.json();
    throw new Error(message || "");
}

export function $(selector) {
    return document.querySelector(selector)
}

export function throwError(msg) {
    alert(`[ERROR]: ${msg}`);
    throw new Error(msg)
}

export function isValidJSON(fileType) {
    return !(!fileType || fileType !== JSON_FILE_TYPE);
}

export function isURL(value) {
    return /^((https?):\/\/)[\w-]*(\.[\w-]+|localhost)+([\w.,@?^=%&:\/~+#-]*[\w@?^=%&\/~+#-])?$/.test(
        value
    )
}

export function parseJSON(data) {
    try {
        JSON.parse(data);
    } catch (err) {
        console.log('parseJSON:', err.message);
        throwError('解析JSON文件失败')
    }
}

export function validateJSON(file) {
    if (!isValidJSON(file.type)) {
        throwError('请选择正确的JSON文件')
    }
    return true
}

export function isValidTemplate(fileType) {
    return !(!fileType || fileType !== TEMPLATE_FILE_TYPE);
}

export function validateTemplateMeta(htmlContent) {
    const parser = new DOMParser();
    const htmlDoc = parser.parseFromString(htmlContent, 'text/html');
    const metas = htmlDoc.getElementsByTagName('meta');
    for (let i = 0; i < metas.length; i++) {
        const metaName = metas[i].getAttribute('name');
        const metaValue = metas[i].getAttribute('value');
        if (metaName === META_KEY && metaValue === TEMPLATE_TYPE) {
            return;
        }
    }
    throwError('请选择从系统导出的模版文件');
}

export function validateTemplate(file) {
    if (!isValidTemplate(file.type)) {
        throwError('请选择正确的模板文件')
    }
    return true
}

export function getReader(fileType, onload) {
    const reader = new FileReader();
    reader.onprogress = () => {
        console.log('loading..')
    };
    reader.onerror = () => {
        alert('[ERROR]:读取' + fileType + '文件失败!')
    };
    reader.onabort = () => {
        alert('[ABORT]:读取' + fileType + '文件失败!')
    };
    reader.onload = () => {
        onload();
    };
    return reader;
}