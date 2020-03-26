/**
 * config:
 * {
 *     selectedClass: //选中颜色
 *     moveClass:     //拖动时颜色
 *     multiSelect: true, //是否支持多选
 *     container:   //父容器
 *     direction: vertical | horizontal //移动方向
 * }
 **/

function $d(selector) {
    return document.querySelectorAll(selector);
}

export function initDrag(config) {
    const instance = {};
    if (!config.container) {
        return;
    }
    const container = document.querySelector(config.container);
    if (!container) {
        return;
    }
    instance.container = container;
    instance.start = function() {
        let mouseDown = false, dragged = false, selectedClass = config.selectedClass || 'drag-active',
            mouseDownTimer = 0, lastSelected = null, isMoving = false;
        function getCurrentNode(current, parent) {
            let item = current;
            while (item.parentNode && item.parentNode != parent) {
                item = item.parentNode;
            }
            return item;
        }

        function removeAllActive() {
            const children = container.children;
            for (let i = 0; i < children.length; i++) {
                children[i].className = children[i].className.replace(new RegExp(selectedClass, 'ig'), '').trim();
            }
        }

        function removeMovingClass() {
            const children = container.children;
            for (let i = 0; i < children.length; i++) {
                if (children[i].className) {
                    children[i].className = children[i].className.replace(new RegExp('drag-moving', 'ig'), '').trim();
                }
            }
        }

        function changeActive(node) {
            if (!node.className || node.className.indexOf(selectedClass) < 0) {
                node.className += ' ' + selectedClass;
                lastSelected = node;
            } else {
                node.className = node.className.replace(new RegExp(selectedClass, 'ig'), '').trim();
            }
        }

        function getNodeIndex(node) {
            const children = container.children;
            for (let i = 0; i < children.length; i++) {
                if (node == children[i]) {
                    return i;
                }
            }
        }

        function shifActive(node) {
            if (lastSelected == null) {
                changeActive(node);
                return;
            }
            const lastSelectedIndex = getNodeIndex(lastSelected), currentIndex = getNodeIndex(node),
                children = container.children,
                start = lastSelectedIndex > currentIndex ? currentIndex : lastSelectedIndex,
                end = lastSelectedIndex > currentIndex ? lastSelectedIndex : currentIndex;
            removeAllActive();
            for (let i = start; i <= end; i++) {
                changeActive(children[i]);
            }
            lastSelected = node;
        }

        function getSelectedNodes() {
            const children = container.children, moveNodes = [];
            for (let i = 0; i < children.length; i++) {
                if (children[i].className && children[i].className.indexOf(selectedClass) >= 0) {
                    moveNodes.push(children[i]);
                    children[i].className += ' drag-moving';
                }
            }
            return moveNodes;
        }

        function handleMouseDwon(event) {
            const node = getCurrentNode(event.target, event.currentTarget);
            if (!node.className || node.className.indexOf(selectedClass) < 0) {
                return;
            }

            clearTimeout(mouseDownTimer);
            mouseDownTimer = setTimeout(function() {
                dragged = true;
            }, 500);
        }

        function handleClick(event) {
            const node = getCurrentNode(event.target, event.currentTarget);
            if (event.ctrlKey || event.metaKey) {
                changeActive(node)
            } else if (event.shiftKey) {
                shifActive(node);
            } else {
                removeAllActive();
                changeActive(node);
            }
        }

        function handleMouseUp(event) {
            clearTimeout(mouseDownTimer);
            removeMovingClass();
            document.body.className = document.body.className.replace(/drag-cursor/ig, '').trim();
            dragged = false;
            isMoving = false;
            alert('mouseup');
        }

        function startMoving() {
            const moveNodes = getSelectedNodes();
            document.body.className += 'drag-cursor';
        }

        function handleMouseMove(event) {
            //debugger;
            if (!dragged) {
               return;
            }
            if (!isMoving) {
                startMoving();
            }
            isMoving = true;
            console.log(dragged);
            console.log(event.clientX, event.clientY);
        }
        container.addEventListener('click', handleClick);
        container.addEventListener('mousedown', handleMouseDwon);
        window.addEventListener('mouseup', handleMouseUp);
        window.addEventListener('mousemove', handleMouseMove);

        instance.destory = function() {
            container.removeEventListener('click', handleClick);
            container.removeEventListener('mousedown', handleMouseDwon);
            window.removeEventListener('mouseup', handleMouseUp);
            window.removeEventListener('mousemove', handleMouseMove);
        }
    }

    return instance;
}

