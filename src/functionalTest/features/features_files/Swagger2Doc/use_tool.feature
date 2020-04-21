Feature: 使用Swagger2Doc小工具预览、更换模板和下载文件
  小工具：Swagger转Doc

  Scenario Outline: 输入一个URL可以看到预览
    Given 用户打开SWagger转Doc工具
    When 用户在URL栏输入"<URL>"
    And 用户点击解析
    Then 用户可以看到预览
    Examples: 
      | URL                                         |
      | https://petstore.swagger.io/v2/swagger.json |

  Scenario: 用户可以下载生成的文档
    Given 用户看到了"https://petstore.swagger.io/v2/swagger.json"的预览
    When 用户点击生成
    Then 用户可以得到一个Word文件

  @skip
  Scenario: 用户可以更换模板并预览
    Given 用户看到了"https://petstore.swagger.io/v2/swagger.json"的预览
    When 用户更换专为功能测试使用的Swagger模板
    Then 用户可以在预览中看到"这是专为功能测试使用的模板"字样