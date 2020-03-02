export const JSON_FILE_TYPE = 'application/json';

export async function validateResponse(response) {
    if (response.status >= 200 && response.status < 300) {
        return response;
    }
    const { message } = await response.json();
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