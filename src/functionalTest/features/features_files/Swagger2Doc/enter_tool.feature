Feature: 在首页点击可以进入Swagger2Doc小工具
  小工具：Swagger转Doc

  Scenario: 从首页点击chiclet的查看按钮可以打开小工具
    Given 用户打开首页
    When 用户点击Swagger chiclet上的Swagger的查看按钮
    Then 用户可以打开Swagger转Doc工具

  Scenario: 从导航栏上点击Swagger转Word可以打开小工具
    Given 用户打开首页
    When 用户点击导航栏上的Swagger转Word按钮
    Then 用户可以打开Swagger转Doc工具