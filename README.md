# bridge2delivery（嘀哒验收小工具）

## 介绍
    嘀哒验收小工具目前主要涉及两个工具。一是，将Swagger文档转换为Word文档；二是，将Cucumber对应的feature文件转换为Excel测试文档。
    目前只兼容chrome浏览器，建议使用chrome浏览器。

## 开发
    springboot开发的一个单体网页应用，当然也可以部署到服务器使用。
    本地编译：./gradlew clean build
    本地运行：./gradlew bootRun
    访问地址：http://localhost:8080

## 测试
    1. 本机已经安装node环境
    2. 执行“./gradlew npminstall”命令下载依赖
    3. 执行“./gradlew functionalTest”命令运行测试

## 使用说明
    1. 本机已经安装jdk8或以上环境
    2. 双击jar包运行
    3. chrome浏览器打开：http://localhost:8080
    
## Swagger转Word小工具
    1. 点击顶部菜单中的[Swagger转Word]菜单，或Swagger logo下方的[查看]按钮，进入到swagger转word页面
    2. 支持两种方式输入Swagger数据源。在输入框中填入swagger对应的json链接，比如：https://petstore.swagger.io/v2/swagger.json ；点击[点击选择文件]按钮选择本地json文件；点击[解析]按钮，预览转成的word文档
    3. 创建自定义的Swagger模板，当默认的word模板样式不满足用户的文档需求，用户也可以自己定义模板样式
       a. 点击顶部菜单栏[创建新模板]菜单，访问自定义模板页面
       b. 点击[加载已有模板]按钮，在已有模板的基础上修改
       c. 点击Swagger logo进入新模板创建页
       
## Cucumber转Excel小工具
    1. 点击顶部菜单中的[Swagger转Excel]菜单，或Swagger logo下方的[查看]按钮，进入到swagger转word页面
    2. 点击[点击选择文件夹]按钮选择本地Cucumber文件夹；点击[解析]按钮，预览转成的word文档
    3. 创建自定义的Cucumber模板，当默认的Excel模板样式不满足用户的文档需求，用户也可以自己定义模板样式
       a. 点击顶部菜单栏[创建新模板]菜单，访问自定义模板页面
       b. 点击[加载已有模板]按钮，在已有模板的基础上修改
       c. 点击Cucumber logo进入新模板创建页
       
## 自定义模板工具
    1. 在右侧富文本编辑器内，可任意输入文本
    2. 双击左侧菜单条目，可在右侧编辑器内插入占位符
    3. 有[array]标志的占位符，在编辑模板时会显示方框，并在使用模板生成内容时产生多个内容（查看示例）
    4. 建议将表格插入在PathList层级内，并保留表格后的换行
    5. 使用“切换HTML代码”按钮，可以直接编辑模板源码
    