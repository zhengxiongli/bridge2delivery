import {$, throwError} from "./utils.js";

/*
config: {
    type: 类型
    click: 点击事件
    dbClick: 双击事件
    container: 容器,
    initCallBack: 初始化完成后回调
}
 */
export function createTemplateTree(config) {
    const tree = {}, nodeMap = [], treeConfig = config;
    let focusPath = '';
    tree.type = config.type;

    tree.focusNode = function (path, disableOthers = true) {
        const pathStr = path == null || path.length === 0 ? tree.data.rootName : path.join('-');
        if (nodeMap.length === 0 || pathStr === focusPath) {
            return;
        }
        focusPath = pathStr;

        for (let i = 0; i < nodeMap.length; i++) {
            const node = nodeMap[i];
            if (pathStr !== node.path) {
                node.node.className = node.node.className.replace(/focused/ig, '').trim();
            } else if (!node.node.className || node.node.className.indexOf('focused') < 0) {
                node.node.className = node.node.className + ' focused';
            }
            if (disableOthers) {
                const regex = '^(' + pathStr + '-)([a-zA-Z0-9]*)$';
                if (new RegExp(regex, 'ig').test(node.path)) {
                    node.node.className = node.node.className.replace(/disabled/ig, '').trim();
                } else if (!node.node.className || node.node.className.indexOf('disabled') < 0) {
                    node.node.className = node.node.className + ' disabled';
                }
            }
        }
    };

    tree.disableAll = function() {
        for (let i = 0; i < nodeMap.length; i++) {
            const node = nodeMap[i];
            if (!node.node.className || node.node.className.indexOf('disabled') < 0) {
                node.node.className = node.node.className + ' disabled';
            }
        }
        focusPath = '';
    }

    tree.enableAll = function(isExcel) {
        for (let i = 0; i < nodeMap.length; i++) {
            const node = nodeMap[i];
            if ((isExcel && node.config.childNodes && node.config.childNodes.length > 0)
                || node.path == tree.data.rootName) {
                continue;
            } else if (!node.node.className || node.node.className.indexOf('disabled') < 0) {
                node.node.className = node.node.className + ' disabled';
            }
            node.node.className = node.node.className.replace(/disabled/ig, '').trim();
        }
        focusPath = '';
    }

    tree.getParentNode = function (nodeInfo) {
        if (nodeInfo.path === tree.data.rootName) {
            return null;
        }
        const parentPath = nodeInfo.path.replace(new RegExp('(-' + nodeInfo.config.name + ')$'), '');
        return tree.getNode(parentPath.split('-'));
    };

    tree.getNode = function (path) {
        const pathStr = path == null || path.length === 0 ? '' : path.join('-');
        for (let i = 0; i < nodeMap.length; i++) {
            if (nodeMap[i].path === pathStr) {
                return nodeMap[i];
            }
        }
        for (let i = 0; i < nodeMap.length; i++) {
            if (new RegExp('.*' + pathStr + '-.*', 'ig').test(nodeMap[i].path)) {
                return nodeMap[i];
            }
        }
        return null;
    };

    function init() {
        if (!config || !config.type || !config.container) {
            throwError("配置错误");
        }
        getConfig(config.type);
    }

    async function validateResponse(response) {
        if (response.status >= 200 && response.status < 300) {
            return response;
        }
        const {message} = await response.json();
        throw new Error(message || "");
    }

    function getConfig(type) {
        fetch('/template/config?type=' + type, {
            method: 'GET'
        })
            .then(validateResponse)
            .then(res => res.json())
            .then(buildTree);
    }

    function buildTree(response) {
        const data = response.data;
        tree.data = data;
        tree.container = $(config.container);
        if (!data.templateNodes || data.templateNodes.length === 0) {
            throwError("配置错误");
        }
        const nodes = data.templateNodes;
        const rootNode = buildRootNode();
        for (let i = 0; i < nodes.length; i++) {
            buildNode(rootNode, nodes[i]);
        }
        !config.initCallBack || config.initCallBack(tree)

    }

    function buildRootNode() {
        if (!tree.data.rootName) {
            return;
        }
        const wrapper = document.createElement('div');
        wrapper.className = 'template-tree-wrapper root';
        const node = document.createElement('div');
        node.className = 'template-tree-node disabled';
        const name = document.createElement('span');
        name.innerText = tree.data.rootName;
        name.className = 'tree-node-name';
        name.style.color = '#333';
        const tip = document.createElement('span');
        tip.innerText = tree.data.rootName;
        tip.className = 'tree-node-tip';
        tip.dataset.path = tree.data.rootName;
        tip.innerText = '👈';
        const description = document.createElement('span');
        description.innerText = tree.data.rootDesc;
        description.className = 'tree-node-desc';
        node.append(name);
        node.append(description);
        node.append(tip);
        wrapper.append(node);
        const nodeInfo = {path: tree.data.rootName, wrapper: wrapper, node: node, config: {name: tree.data.rootName}, isRoot: true};
        node.addEventListener('click', function (e) {
            if (isExpandClick(e)) {
                expandClick(e);
            }
        });
        nodeMap.push(nodeInfo);
        tree.container.append(wrapper);
        const expand = document.createElement('span');
        expand.className = 'expand';
        node.prepend(expand);
        return nodeInfo;
    }

    function addNodeHoverStyle(node) {
        node.className = 'template-tree-node';
        node.addEventListener('mouseover', function () {
            if (node.classList.contains('disabled')) {
                return;
            }
            node.classList.add('font-strong');
        });
        node.addEventListener('mouseout', function () {
            if (node.classList.contains('disabled')) {
                return;
            }
            node.classList.remove('font-strong')
        });
    }

    function buildNode(parentNode, config) {
        const wrapper = document.createElement('div');
        wrapper.className = 'template-tree-wrapper';
        const node = document.createElement('div');
        addNodeHoverStyle(node);
        const name = document.createElement('span');
        name.innerText = config.name + ':';
        name.className = 'tree-node-name';
        if (config.isArray) {
            name.innerHTML = config.name + '<span style="color: #ec7375">[array]</span>:';
        }
        tree.data.fileType === 'EXCEL' && config.childNodes && config.childNodes.length > 0 && (node.className += ' disabled');
        const description = document.createElement('span');
        description.innerText = config.description;
        description.className = 'tree-node-desc';
        const tip = document.createElement('span');
        tip.innerText = tree.data.rootName;
        tip.className = 'tree-node-tip';
        tip.dataset.path = tree.data.rootName;
        tip.innerText = '👈';
        const path = !!parentNode && !!parentNode.path ? parentNode.path + '-' + config.name : config.name;
        node.dataset.path = path;
        node.append(name);
        node.append(description);
        node.append(tip);
        wrapper.append(node);
        const nodeInfo = {path: path, wrapper: wrapper, config: config, node: node};

        node.addEventListener('click', function (e) {
            if (isExpandClick(e)) {
                expandClick(e);
                return;
            }
            if (/.*disabled.*/ig.test(node.className)) {
                return;
            }
            treeConfig.click && treeConfig.click(e, nodeInfo);
        });
        node.addEventListener('dblclick', function (e) {
            if (/.*disabled.*/ig.test(node.className)) {
                return;
            }
            treeConfig.dbClick && treeConfig.dbClick(e, nodeInfo);
        });
        nodeMap.push(nodeInfo);
        if (!!parentNode) {
            parentNode.wrapper.append(wrapper);
        } else {
            tree.container.append(wrapper);
        }
        if (!config.childNodes || config.childNodes.length === 0) {
            return;
        }
        const expand = document.createElement('span');
        expand.className = 'expand';
        node.prepend(expand);
        for (let i = 0; i < config.childNodes.length; i++) {
            buildNode(nodeInfo, config.childNodes[i]);
        }
    }

    function isExpandClick(e) {
        const expandItem = e.target;
        return /span/ig.test(expandItem.tagName) &&
            (expandItem.className === 'expand' || expandItem.className === 'collapse');
    }

    function expandClick(e) {
        const expandItem = e.target;
        const parent = expandItem.parentElement;
        if (expandItem.className === 'expand') {
            expandItem.className = 'collapse';
            collapseChildren(parent.parentElement);
        } else {
            expandItem.className = 'expand';
            expandChildren(parent.parentElement);
        }
    }

    function expandChildren(node) {
        const children = node.children;
        if (!children || children.length === 0) {
            return;
        }
        for (let i = 0; i < children.length; i++) {
            if (!isWrapper(children[i])) {
                continue;
            }
            children[i].style.display = 'block';
        }
    }

    function collapseChildren(node) {
        const children = node.children;
        if (!children || children.length === 0) {
            return;
        }
        for (let i = 0; i < children.length; i++) {
            if (!isWrapper(children[i])) {
                continue;
            }
            children[i].style.display = 'none';
        }
    }

    function isWrapper(node) {
        return /.*template-tree-wrapper.*/ig.test(node.className);
    }

    init();
    return tree;
}