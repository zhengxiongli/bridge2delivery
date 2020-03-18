
/**
 * config {
 *     title: 标题
 *     closeBtn: 是否显示closeBtn
 *     content: 显示内容,
 *     contentWidth: 显示内容宽度,
 *     buttons: [{text: 'text', type: [check, button], align: [left, right], onClick: function(){}}]
 *     beforeShow: function() {}
 *     afterShow: function() {}
 *     onClose: function()
 * }
 * @returns {*}
 */
export function newModal(config) {
    const modal = {};
    function defaultFunc() {};
    config.beforeShow = config.beforeShow || defaultFunc;
    config.afterShow = config.afterShow || defaultFunc;
    config.onClose = config.onClose || defaultFunc;

    function loadModalCss() {
        let css = document.querySelector('link#modal');
        if (css) {
            return;
        }
        const link = document.createElement('link');
        link.setAttribute('rel', 'stylesheet');
        link.setAttribute('type', 'text/css');
        link.setAttribute('id', 'modal');
        link.setAttribute('href', '/css/modal/modal.css');
        document.getElementsByTagName('head').item(0).append(link);
    }

    function createHeader() {
        const header = document.createElement('div'), title = document.createElement('div'),
            close = document.createElement('div'), closeText = document.createElement('span');
        title.innerText = config.title;
        title.className = 'modal-title';
        header.className = 'modal-header';
        closeText.innerText = '×';
        close.className = 'modal-close';
        header.append(title);
        close.append(closeText);
        if (config.closeBtn) {
            header.append(close);
        }
        closeText.addEventListener('click', function () {
            modal.close();
        });
        return header;
    }

    function createButtons() {
        const buttonContainer = document.createElement('div'), left = document.createElement('div'),
                right = document.createElement('div');
        left.className = 'left';
        right.className = 'right';
        buttonContainer.className = 'modal-buttons';
        if (!config.buttons || config.buttons.length == 0) {
            return buttonContainer;
        }
        for (let i = 0; i < config.buttons.length; i++) {
            if (config.buttons[i].type == 'check') {
                createCheckBox(config.buttons[i], left, right);
            } else {
                createButton(config.buttons[i], left, right);
            }
        }
        buttonContainer.append(left);
        buttonContainer.append(right);
        return buttonContainer;
    }

    function createButton(btnConfig, left, right) {
        const button = document.createElement('button');
        button.innerText = btnConfig.text;
        button.addEventListener('click', function () {
            btnConfig.onClick && btnConfig.onClick.apply(this);
        });
        btnConfig.align && btnConfig.align == 'left' ? left.append(button) : right.append(button);
    }

    function createCheckBox(btnConfig, left, right) {
        const label = document.createElement('label'), checkBox = document.createElement('input'),
            icon = document.createElement('span'), text = document.createElement('span');
        checkBox.setAttribute('type', 'checkbox');
        text.innerText = btnConfig.text;
        if (btnConfig.id) {
            checkBox.setAttribute('id', btnConfig.id);
        }
        label.append(checkBox);
        label.append(icon);
        label.append(text);
        btnConfig.align && btnConfig.align == 'left' ? left.append(label) : right.append(label);
    }

    modal.show = function() {
        if (modal.window) {
            modal.close();
        }
        loadModalCss();
        const modalWindow = document.createElement('div'), container = document.createElement('div'),
                content = document.createElement('div');
        modalWindow.className = 'modal-window';
        container.className = 'modal-container';
        content.className = 'modal-content';
        if (config.contentWidth) {
            content.style.width = config.contentWidth;
        }
        modalWindow.append(container);
        content.innerHTML = config.content;
        container.append(createHeader());
        container.append(content);
        if (config.buttons && config.buttons.length > 0) {
            container.append(createButtons());
        }
        modalWindow.style.display = 'none';
        modal.window = modalWindow;
        document.body.append(modalWindow);
        config.beforeShow && config.beforeShow.apply(modal);
        modalWindow.style.display = '';
        config.afterShow && config.afterShow.apply(modal);
    }

    modal.close = function() {
        modal.window.style.display = 'none';
        modal.window.parentNode.removeChild(modal.window);
        modal.window == null;
    }

    return modal;
}
