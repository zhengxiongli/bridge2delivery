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
    //预览URL
    tree.previewUrl = '';
    //默认模板url
    tree.defaultUrl = '';
    //root节点
    tree.rootName = '';
    //
    tree.rootDesc = '';

    tree.focusNode = function(path) {
        const pathStr = path == null || path.length === 0 ? '' : path.join('-');
        if (pathStr === focusPath) {
            return;
        }
        focusPath = pathStr;
        for (let i = 0; i < nodeMap.length; i++) {
            const node = nodeMap[i];
            if (new RegExp('.*' + pathStr + '-.*', 'ig').test(node.path)) {
                node.node.className = node.node.className.replace('disabled', '').trim();
            } else {
                node.node.className = node.node.className + ' disabled';
            }
        }
    };

    tree.getNode = function(path) {
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
    }

    function init() {
        if (!config || !config.type || !config.container) {
            throwError("配置错误");
        }
        getConfig(config.type);
    }

    function getConfig(type) {
        fetch('/template/config?type=' + type, {
            method: 'GET'
        })
            .then(res => res.json())
            .then(buildTree).then(!config.initCallBack || config.initCallBack());
    }

    function buildTree(response) {
        const data = response.data;
        tree.previewUrl = data.previewUrl;
        tree.defaultUrl = data.defaultUrl;
        tree.rootName = data.rootName;
        tree.rootDesc = data.rootDesc;
        tree.container = $(config.container);
        if (!data.templateNodes || data.templateNodes.length === 0) {
            throwError("配置错误");
        }
        const nodes = data.templateNodes;
        const rootNode = buildRootNode(treeConfig.root);
        for (let i = 0; i < nodes.length; i++) {
            buildNode(rootNode, nodes[i]);
        }
    }

    function buildRootNode() {
        const wrapper = document.createElement('div');
        wrapper.className = 'template-tree-wrapper root';
        const node = document.createElement('div');
        node.className = 'template-tree-node disabled';
        const name = document.createElement('span');
        name.innerText = tree.rootName;
        name.className = 'tree-node-name';
        name.style.color = '#333';
        node['data-path'] = tree.rootName;
        const description = document.createElement('span');
        description.innerText = tree.rootDesc;
        description.className = 'tree-node-desc';
        node.append(name);
        node.append(description);
        wrapper.append(node);
        const nodeInfo = {path: tree.rootName, wrapper: wrapper, node: node};
        node.addEventListener('click', function(e) {
            if (isExpandClick(e)) {
                expandClick(e, node);
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
        node.className = 'template-tree-node'
        node.addEventListener('mouseover', function(e) {
            if (node.classList.contains('disabled')) {
                return;
            }
            node.classList.add('font-strong');
        });
        node.addEventListener('mouseout', function(e) {
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
        const description = document.createElement('span');
        description.innerText = config.description;
        description.className = 'tree-node-desc';
        const path = !!parentNode && !!parentNode.path ? parentNode.path + '-' + config.name : config.name;
        node['data-path'] = path;
        node.append(name);
        node.append(description);
        wrapper.append(node);
        const nodeInfo = {path: path, wrapper: wrapper, config: config, node: node};

        node.addEventListener('click', function(e) {
            if (isExpandClick(e)) {
                expandClick(e, node);
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

    function expandClick(e, node) {
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