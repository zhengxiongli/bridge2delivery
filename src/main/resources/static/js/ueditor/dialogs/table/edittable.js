/**
 * Created with JetBrains PhpStorm.
 * User: xuheng
 * Date: 12-12-19
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
(function () {
    var title = $G("J_title"),
        titleCol = $G("J_titleCol"),
        caption = $G("J_caption"),
        sorttable = $G("J_sorttable"),
        autoSizeContent = $G("J_autoSizeContent"),
        autoSizePage = $G("J_autoSizePage"),
        setSizePage = $G("J_setSizePage"),
        setSizeText = $G("J_setText"),
        setCenter = $G('J_setCenter'),
        setCenterLabel = $G('J_setCenterLabel'),
        tone = $G("J_tone"),
        backTone = $G("J_back_tone"),
        me,
        preview = $G("J_preview");

    var editTable = function () {
        me = this;
        me.init();
    };
    editTable.prototype = {
        init:function () {
            var colorPiker = new UE.ui.ColorPicker({
                    editor:editor
                }),
                colorPop = new UE.ui.Popup({
                    editor:editor,
                    content:colorPiker
                }),
                backColorPiker = new UE.ui.ColorPicker({
                    editor:editor
                }),
                backColorPop = new UE.ui.Popup({
                    editor:editor,
                    content:backColorPiker
                });

            title.checked = editor.queryCommandState("inserttitle") == -1;
            titleCol.checked = editor.queryCommandState("inserttitlecol") == -1;
            caption.checked = editor.queryCommandState("insertcaption") == -1;
            sorttable.checked = editor.queryCommandState("enablesort") == 1;

            var enablesortState = editor.queryCommandState("enablesort"),
                disablesortState = editor.queryCommandState("disablesort");

            sorttable.checked = !!(enablesortState < 0 && disablesortState >=0);
            sorttable.disabled = !!(enablesortState < 0 && disablesortState < 0);
            sorttable.title = enablesortState < 0 && disablesortState < 0 ? lang.errorMsg:'';
            me.createTable(title.checked, titleCol.checked, caption.checked);
            me.setAutoSize();
            me.setColor(me.getColor());
            me.setBackground(me.getBackGroundColor());

            domUtils.on(title, "click", me.titleHanler);
            domUtils.on(titleCol, "click", me.titleColHanler);
            domUtils.on(caption, "click", me.captionHanler);
            domUtils.on(sorttable, "click", me.sorttableHanler);
            domUtils.on(autoSizeContent, "click", me.autoSizeContentHanler);
            domUtils.on(autoSizePage, "click", me.autoSizePageHanler);
            domUtils.on(setSizePage, "click", me.setSizePageHanler);
            domUtils.on(setSizeText, 'change', me.setSizeTextHandler);
            domUtils.on(setCenter, 'click', me.setCenterHandler);

            domUtils.on(tone, "click", function () {
                colorPop.showAnchor(tone);
            });
            domUtils.on(document, 'mousedown', function () {
                colorPop.hide();
            });
            colorPiker.addListener("pickcolor", function () {
                me.setColor(arguments[1]);
                colorPop.hide();
            });
            colorPiker.addListener("picknocolor", function () {
                me.setColor("");
                colorPop.hide();
            });
            domUtils.on(backTone, 'click', function() {
                backColorPop.showAnchor(backTone);
            });
            domUtils.on(document, 'mousedown', function() {
                backColorPop.hide();
            });
            backColorPiker.addListener("pickcolor", function () {
                me.setBackground(arguments[1]);
                backColorPop.hide();
            });
            backColorPiker.addListener("picknocolor", function () {
                me.setBackground("");
                colorPop.hide();
            });

            setSizePage.checked = true;
        },

        createTable:function (hasTitle, hasTitleCol, hasCaption) {
            var arr = [],
                sortSpan = '<span>^</span>';
            arr.push("<table id='J_example'>");
            if (hasCaption) {
                arr.push("<caption>" + lang.captionName + "</caption>")
            }
            if (hasTitle) {
                arr.push("<tr>");
                if(hasTitleCol) { arr.push("<th>" + lang.titleName + "</th>"); }
                for (var j = 0; j < 5; j++) {
                    arr.push("<th>" + lang.titleName + "</th>");
                }
                arr.push("</tr>");
            }
            for (var i = 0; i < 6; i++) {
                arr.push("<tr>");
                if(hasTitleCol) { arr.push("<th>" + lang.titleName + "</th>") }
                for (var k = 0; k < 5; k++) {
                    arr.push("<td>" + lang.cellsName + "</td>")
                }
                arr.push("</tr>");
            }
            arr.push("</table>");
            preview.innerHTML = arr.join("");
            this.updateSortSpan();
        },
        titleHanler:function () {
            var example = $G("J_example"),
                frg=document.createDocumentFragment(),
                color = domUtils.getComputedStyle(domUtils.getElementsByTagName(example, "td")[0], "border-color"),
                colCount = example.rows[0].children.length;

            if (title.checked) {
                example.insertRow(0);
                for (var i = 0, node; i < colCount; i++) {
                    node = document.createElement("th");
                    node.innerHTML = lang.titleName;
                    frg.appendChild(node);
                }
                example.rows[0].appendChild(frg);

            } else {
                domUtils.remove(example.rows[0]);
            }
            me.setColor(color);
            me.updateSortSpan();
        },
        titleColHanler:function () {
            var example = $G("J_example"),
                color = domUtils.getComputedStyle(domUtils.getElementsByTagName(example, "td")[0], "border-color"),
                colArr = example.rows,
                colCount = colArr.length;

            if (titleCol.checked) {
                for (var i = 0, node; i < colCount; i++) {
                    node = document.createElement("th");
                    node.innerHTML = lang.titleName;
                    colArr[i].insertBefore(node, colArr[i].children[0]);
                }
            } else {
                for (var i = 0; i < colCount; i++) {
                    domUtils.remove(colArr[i].children[0]);
                }
            }
            me.setColor(color);
            me.updateSortSpan();
        },
        captionHanler:function () {
            var example = $G("J_example");
            if (caption.checked) {
                var row = document.createElement('caption');
                row.innerHTML = lang.captionName;
                example.insertBefore(row, example.firstChild);
            } else {
                domUtils.remove(domUtils.getElementsByTagName(example, 'caption')[0]);
            }
        },
        sorttableHanler:function(){
            me.updateSortSpan();
        },
        autoSizeContentHanler:function () {
            var example = $G("J_example");
            example.removeAttribute("width");
            setSizeText.style.display = 'none';
            setCenterLabel.style.display = 'none';
            example.style.margin = '';
            setCenter.checked = false;
        },
        autoSizePageHanler:function () {
            var example = $G("J_example");
            var tds = example.getElementsByTagName(example, "td");
            utils.each(tds, function (td) {
                td.removeAttribute("width");
            });
            example.setAttribute('width', '100%');
            setSizeText.style.display = 'none';
            setCenterLabel.style.display = 'none';
            example.style.margin = '';
            setCenter.checked = false;
        },
        setSizePageHanler:function() {
            var example = $G('J_example');
            setSizeText.style.display = 'inline-block';
            setCenterLabel.style.display = 'inline-block';
            var tds = example.getElementsByTagName(example, "td");
            utils.each(tds, function (td) {
                td.removeAttribute("width");
            });
            example.setAttribute('width', setSizeText.value);
        },
        setSizeTextHandler:function() {
            if (!setSizePage.checked) {
                return;
            }
            var example = $G('J_example');
            example.setAttribute('width', setSizeText.value);
        },
        setCenterHandler:function() {
            var example = $G('J_example');
            if (setCenter.checked) {
                example.style.margin = '0 auto';
            } else {
                example.style.margin = '';
            }

        },
        updateSortSpan: function(){
            var example = $G("J_example"),
                row = example.rows[0];

            var spans = domUtils.getElementsByTagName(example,"span");
            utils.each(spans,function(span){
                span.parentNode.removeChild(span);
            });
            if (sorttable.checked) {
                utils.each(row.cells, function(cell, i){
                    var span = document.createElement("span");
                    span.innerHTML = "^";
                    cell.appendChild(span);
                });
            }
        },
        getColor:function () {
            var start = editor.selection.getStart(), color,
                cell = domUtils.findParentByTagName(start, ["td", "th", "caption"], true);
            color = cell && domUtils.getComputedStyle(cell, "border-color");
            if (!color)  color = "#DDDDDD";
            return color;
        },
        getBackGroundColor: function() {
            var start = editor.selection.getStart(), color,
                table = domUtils.findParentByTagName(start, ["table"], true);
            color = table && domUtils.getComputedStyle(table, "background-color");
            if (!color) color = '';
            return color;
        },
        setColor:function (color) {
            var example = $G("J_example"),
                arr = domUtils.getElementsByTagName(example, "td").concat(
                    domUtils.getElementsByTagName(example, "th"),
                    domUtils.getElementsByTagName(example, "caption")
                );

            tone.value = color;
            utils.each(arr, function (node) {
                node.style.borderColor = color;
            });

        },
        setBackground: function(color) {
            var example = $G('J_example');
            backTone.value = color;
            example.style.backgroundColor = color;
        },
        setAutoSize:function () {
            var me = this;
            setSizePage.checked = true;
            var start = editor.selection.getStart(),
                table = start && domUtils.findParentByTagName(start, ["table"]);
            if (table) {
                setCenter.checked = !!table.getAttribute('align');
                setSizeText.value = !!table.getAttribute('width') ? table.getAttribute('width') : '100%';
            }
            me.setSizePageHanler();
            me.setCenterHandler();
        }
    };

    new editTable;

    dialog.onok = function () {
        editor.__hasEnterExecCommand = true;

        var checks = {
            title:"inserttitle deletetitle",
            titleCol:"inserttitlecol deletetitlecol",
            caption:"insertcaption deletecaption",
            sorttable:"enablesort disablesort"
        };
        editor.fireEvent('saveScene');
        for(var i in checks){
            var cmds = checks[i].split(" "),
                input = $G("J_" + i);
            if(input["checked"]){
                editor.queryCommandState(cmds[0])!=-1 &&editor.execCommand(cmds[0]);
            }else{
                editor.queryCommandState(cmds[1])!=-1 &&editor.execCommand(cmds[1]);
            }
        }

        editor.execCommand("edittable", tone.value, backTone.value);
        autoSizeContent.checked ?editor.execCommand('adaptbytext') : "";
        autoSizePage.checked ? editor.execCommand("adaptbywindow") : "";
        setSizePage.checked ? editor.execCommand("settablesize", setSizeText.value, setCenter.checked) : '';
        editor.fireEvent('saveScene');

        editor.__hasEnterExecCommand = false;
    };
})();