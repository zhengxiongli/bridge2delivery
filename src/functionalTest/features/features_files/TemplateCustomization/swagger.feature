Feature: Swagger模板编辑
进入模板自定义工具之后，制作Swagger的模板

  Scenario: 从导航栏进入模板自定义工具
    Given 用户打开首页
    When 用户点击导航栏上的创建新模版按钮
    Then 用户可以打开创建新模板工具

  Scenario: 打开编辑Swagger模板的页面
    Given 用户打开创建新模板工具
    When 用户点击创建新模板工具的Swagger按钮
    And 用户在使用说明框点击确定
    Then 用户可以看到编辑Swagger模板的页面

  Scenario: 用户可以下载Swagger模板
    Given 用户打开编辑Swagger模板的页面
    And 用户在使用说明框点击确定
    When 用户点击模板自定义的生成按钮
    Then 用户可以下载Swagger模板