/**
 * config:
 * {
 *     selectedClass: //选中颜色
 *     moveClass:     //拖动时颜色
 *     multiSelect: true, //是否支持多选
 *     container:   //父容器
 *     direction: vertical | horizontal //移动方向,
 *     filterItem: function(item) {}
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
        let mouseDown = false, dragged = false, selectedClass = config.selectedClass || 'drag-selected',
            mouseDownTimer = 0, lastSelected = null, isMoving = false, currentNode, moveContainer,
            movingNodes, lastPreNode, lastNextNode, lastLocation;
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
            if (config.filterItem && config.filterItem(node)) {
                return;
            }
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
            const children = container.children, selectedNodes = [];
            for (let i = 0; i < children.length; i++) {
                if (children[i].className && children[i].className.indexOf(selectedClass) >= 0) {
                    selectedNodes.push(children[i]);
                    children[i].className += ' drag-moving';
                }
            }
            return selectedNodes;
        }

        function handleMouseDwon(event) {
            const node = currentNode = getCurrentNode(event.target, event.currentTarget);
            if (!node.className || node.className.indexOf(selectedClass) < 0) {
                return;
            }

            clearTimeout(mouseDownTimer);
            dragged = true;
        }

        function handleClick(event) {
            if (isMoving) {
                return;
            }
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
            moveContainer && moveContainer.remove();
            moveContainer = null;
            dragged = false;
            isMoving = false;
            lastNextNode = null;
            lastPreNode = null;
            lastLocation = null;
        }

        function getMaxWidth(left, width) {
            if (width + left + 10 < document.body.offsetWidth) {
                return width;
            }
            return document.body.offsetWidth - 10 - left;
        }

        function startMoving(currentNode) {
            const selectedNodes = getSelectedNodes(), rect = currentNode.getBoundingClientRect();
            movingNodes = selectedNodes;
            document.body.className += 'drag-cursor';
            moveContainer = document.createElement('div');
            moveContainer.style.position = 'fixed';
            moveContainer.className = 'drag-move-container';
            moveContainer.style.top = rect.y + 'px';
            moveContainer.style.left = rect.x + 'px';
            moveContainer.style.width = getMaxWidth(rect.x, container.offsetWidth) + 'px';
            let offset = 0, zIndex = 1000;
            for (let i = 0; i < selectedNodes.length; i++) {
                const node = selectedNodes[i].cloneNode(true);
                node.className = node.className.replace(/drag-moving/ig, '').trim();
                node.style.position = 'absolute';
                node.style.width = '100%';
                node.style.top = offset + 'px';
                node.style.left = offset + 'px';
                offset += 2;
                node.style.zIndex = zIndex--;
                moveContainer.append(node);
            }
            moveContainer.setAttribute('data-number', selectedNodes.length);
            container.append(moveContainer);
        }

        function isMovingNodes(node) {
            for (let i = 0; i < movingNodes.length; i++) {
                if (node == movingNodes[i]) {
                    return true;
                }
            }
            return false;
        }

        function moveNodes(x, y) {
            if (!moveContainer) {
                return;
            }
            const top = moveContainer.getBoundingClientRect().y, nodes = [];
            let mouseOnNode = null, location, preNode, nextNode;
            for (let i = 0; i < container.children.length; i++) {
                if (container.children[i] == moveContainer) {
                    continue;
                }
                nodes.push(container.children[i]);
            }
            if (top < nodes[0].getBoundingClientRect().y) {
                mouseOnNode = nodes[0];
            } else if (top > nodes[nodes.length - 1].getBoundingClientRect().y + nodes[nodes.length - 1].offsetHeight){
                mouseOnNode = nodes[nodes.length - 1];
            } else {
                for (let i = 0; i < nodes.length; i++) {
                    if (nodes[i] == moveContainer) {
                        continue;
                    }
                    if (top > nodes[i].getBoundingClientRect().y && nodes[i].getBoundingClientRect().y + nodes[i].offsetHeight > top) {
                        mouseOnNode = nodes[i];
                        break;
                    }
                }
            }
            if (!mouseOnNode || isMovingNodes(mouseOnNode)) {
                return;
            }
            //判断是放在鼠标所在节点的前面还是后面
            location = top > mouseOnNode.getBoundingClientRect().y + mouseOnNode.offsetHeight / 2 ? 'after' : 'before';
            //获取移动后移动元素前一个节点
            preNode = location === 'before' ? getPreSibling(mouseOnNode) : mouseOnNode;
            //获取移动后移动元素后一个节点
            nextNode = location === 'after' ? getNexSibling(mouseOnNode) : mouseOnNode;

            //所有都一样，不用移动
            if (preNode === lastPreNode && lastNextNode === nextNode && lastLocation === location) {
                return;
            }
            //当前节点前就是移动的元素，不需要重复向前移动
            if (location === 'before' && isMovingNodes(mouseOnNode.previousNode || mouseOnNode.previousElementSibling)) {
                return;
            }
            //当前节点后就是移动元素，不需要重复向后移动
            if (location === 'after' && isMovingNodes(mouseOnNode.nextSibling)) {
                return;
            }
            if (lastLocation !== location) {
                //当前节点是上一次移动后移动元素后面的一个节点，并需要放在此节点前，因为已经在此节点前了，不要重复移动
                if (location === 'before' && mouseOnNode === lastNextNode) {
                    return;
                }
                //当前节点是上一次移动后移动元素前面的一个节点，并需要放在此节点后，因为已经在此节点后了，不要重复移动
                if (location === 'after' && mouseOnNode === lastPreNode) {
                    return;
                }
            }
            lastPreNode = preNode;
            lastNextNode = nextNode;
            lastLocation = location;
            translate(location, mouseOnNode);
        }

        function getPreSibling(mouseOnNode) {
            let pre = mouseOnNode.previousNode || mouseOnNode.previousElementSibling;
            while (isMovingNodes(pre)) {
                pre = pre.previousNode;
            }
            return pre;
        }

        function getNexSibling(mouseOnNode) {
            let next = mouseOnNode.nextSibling;
            while (isMovingNodes(next)) {
                next = next.nextSibling;
            }
            return next;
        }

        function translate(location, mouseOnNode) {
            const translateY = location === 'before' ? -mouseOnNode.offsetHeight : mouseOnNode.offsetHeight;
            let mouseOnNodeTranslateY = 0;
            for (let i = 0; i < movingNodes.length; i++) {
                movingNodes[i].style.transition = 'all 150ms';
                movingNodes[i].style.transform = `translate3d(0px, ${translateY}px, 0px)`;
                mouseOnNodeTranslateY += movingNodes[i].offsetHeight;
            }
            mouseOnNode.style.transition = 'all 150ms';
            mouseOnNode.style.transform = `translate3d(0px, ${lastLocation === 'before' ? mouseOnNodeTranslateY : -mouseOnNodeTranslateY}px, 0px)`;
            setTimeout(function() {
                mouseOnNode.style.transition = '';
                mouseOnNode.style.transform = '';
                for (let i = 0; i < movingNodes.length; i++) {
                    movingNodes[i].remove();
                    movingNodes[i].style.transition = '';
                    movingNodes[i].style.transform = '';
                    if (location === 'before') {
                        container.insertBefore(movingNodes[i], mouseOnNode);
                    } else if (mouseOnNode.nextSibling) {
                        container.insertBefore(movingNodes[i], mouseOnNode.nextSibling);
                    } else {
                        container.appendChild(movingNodes[i]);
                    }
                }
            }, 200)

        }

        function handleMouseMove(event) {
            if (!dragged) {
               return;
            }
            if (!isMoving) {
                startMoving(currentNode);
            }
            isMoving = true;
            moveContainer.style.top = event.y + 'px';
            moveNodes(event.x, event.y);
        }
        container.addEventListener('click', handleClick);
        container.addEventListener('mousedown', handleMouseDwon);
        window.addEventListener('mouseup', handleMouseUp);
        window.addEventListener('mousemove', handleMouseMove);
        container.className += ' drag-unselected';

        instance.destory = function() {
            container.removeEventListener('click', handleClick);
            container.removeEventListener('mousedown', handleMouseDwon);
            window.removeEventListener('mouseup', handleMouseUp);
            window.removeEventListener('mousemove', handleMouseMove);
            container.className = container.className.replace(/drag-unselected/ig, '').trim();
        }
    }

    return instance;
}

