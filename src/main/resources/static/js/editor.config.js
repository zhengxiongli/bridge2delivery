export default {
    enableAutoSave: true,
    elementPathEnabled: false,
    wordCount: false,
    maximumWords: 9999999999,
    focus: true,
    autoHeightEnabled: false,
    autoFloatEnabled: true,
    toolbars: [
        [
            'source',//源码
            //'preview', //预览
            'undo', //撤销
            'redo', //重做
            'formatmatch', //格式刷
            '|', //分隔符
            'paragraph', //段落格式
            '|', //分隔符
            'fontfamily', //字体
            '|', //分隔符
            'fontsize', //字号
            '|', //分隔符
            'bold', //加粗
            'italic', //斜体
            'underline', //下划线
            //'indent', //首行缩进
            'forecolor', //字体颜色
            'backcolor', //背景色
            'link', //超链接
            '|', //分隔符
            'mergecells', //合并多个单元格
            'splittocells', //完全拆分单元格
            '|', //分隔符
            'justifyleft', //居左对齐
            'justifycenter', //居中对齐
            'justifyright', //居右对齐
            'justifyjustify', //两端对齐
            '|', //分隔符
            'inserttable', //插入表格
            'insertrow', //前插入行
            'insertcol', //前插入列
            '|', //分隔符
            'drafts', // 从草稿箱加载
            '|', //分隔符
            'fullscreen' //全屏
            //'strikethrough', //删除线
            //'subscript', //下标
            //'fontborder', //字符边框
            //'superscript', //上标
            //'blockquote', //引用
            //'horizontal', //分隔线
            //'removeformat', //清除格式
            //'mergeright', //右合并单元格
            //'mergedown', //下合并单元格
            //'deleterow', //删除行
            //'deletecol', //删除列
            //'splittorows', //拆分成行
            //'splittocols', //拆分成列
            //'deletecaption', //删除表格标题
            //'inserttitle', //插入标题
            //'deletetable', //删除表格
            //'cleardoc', //清空文档
            //'insertparagraphbeforetable', //"表格前插入行"
            //'edittable', //表格属性
            //'edittd', //单元格属性
            //'searchreplace', //查询替换
            //'insertorderedlist', //有序列表
            //'insertunorderedlist', //无序列表
            //'directionalityltr', //从左向右输入
            //'directionalityrtl', //从右向左输入
            //'rowspacingtop', //段前距
            //'rowspacingbottom', //段后距
            //'imageleft', //左浮动
            //'imageright', //右浮动
            //'imagecenter', //居中
            //'lineheight', //行间距
            //'edittip ', //编辑提示
            //'customstyle', //自定义标题
            //'touppercase', //字母大写
            //'tolowercase', //字母小写
        ]
    ],
    head: ['<meta charset="UTF-8" http-equiv="Content-Type" content="application/msword"/>']
}