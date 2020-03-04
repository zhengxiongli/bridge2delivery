import {$, throwError} from "./utils.js";

/*
config: {
    type: 类型
    click: 点击事件
    dbClick: 双击事件
    container: 容器,
    root: 根节点描述
}
 */
export function createTemplateTree(config) {
    const tree = {}, nodeMap = [];
    //预览URL
    tree.previewUrl = '';
    //默认模板url
    tree.defaultUrl = '';

    tree.focusNode = function(path) {

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
            .then(buildTree);
    }

    function buildTree(response) {
        const data = response.data;
        tree.previewUrl = data.previewUrl;
        tree.defaultUrl = data.defaultUrl;
        tree.container = $(config.container);
        if (!data.templateNodes || data.templateNodes.length == 0) {
            throwError("配置错误");
        }
        const nodes = data.templateNodes;
        const rootNode = buildRootNode(config.root);
        for (let i = 0; i < nodes.length; i++) {
            buildNode(rootNode, nodes[i]);
        }
    }

    function buildRootNode(root) {
        const wrapper = document.createElement('div');
        wrapper.className = 'template-tree-wrapper root';
        const node = document.createElement('div');
        node.className = 'template-tree-node';
        const name = document.createElement('span');
        name.innerText = root || 'root';
        name.className = 'tree-node-name';
        name.style.color = '#333';
        node['data-path'] = '';
        node.append(name);
        wrapper.append(node);
        const nodeInfo = {path: '', dom: wrapper};
        node.addEventListener('click', function(e) {
            if (isExpandClick(e)) {
                exapandClick(e, node);
                return;
            }
        });
        nodeMap.push(nodeInfo);
        tree.container.append(wrapper);
        const expand = document.createElement('span');
        expand.className = 'expand';
        node.prepend(expand);
        return nodeInfo;
    }

    function buildNode(parentNode, config) {
        const wrapper = document.createElement('div');
        wrapper.className = 'template-tree-wrapper';
        const node = document.createElement('div');
        node.className = 'template-tree-node';
        const name = document.createElement('span');
        name.innerText = config.name + ':';
        name.className = 'tree-node-name';
        const description = document.createElement('span');
        description.innerText = config.description;
        description.className = 'tree-node-desc';
        const path = !!parentNode ? parentNode.path + '-' + config.name : config.name;
        node['data-path'] = path;
        node.append(name);
        node.append(description);
        wrapper.append(node);
        const nodeInfo = {path: path, dom: wrapper, config: config};
        node.addEventListener('click', function(e) {
            if (isExpandClick(e)) {
                exapandClick(e, node);
                return;
            }
            config.click && config.click(e, node, nodeInfo);
        });
        node.addEventListener('dbclick', function (e) {
            config.dbClick && config.dbClick(e, node, nodeInfo);
        });
        nodeMap.push(nodeInfo);
        if (!!parentNode) {
            parentNode.dom?.append(wrapper);
        } else {
            tree.container.append(wrapper);
        }
        if (!config.childNodes || config.childNodes.length == 0) {
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
            (expandItem.className == 'expand' || expandItem.className == 'collapse');
    }

    function exapandClick(e, node) {
        const expandItem = e.target;
        const parent = expandItem.parentElement;
        if (expandItem.className == 'expand') {
            expandItem.className = 'collapse';
            collapseChildren(parent.parentElement);
        } else {
            expandItem.className = 'expand';
            expandChildren(parent.parentElement);
        }
    }

    function expandChildren(node) {
        const children = node.children;
        if (!children || children.length == 0) {
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
        if (!children || children.length == 0) {
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