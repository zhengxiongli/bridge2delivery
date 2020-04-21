Feature: 在首页点击可以进入Cucumber2Excel小工具
  小工具：Cucumber转Excel

  Scenario: 从首页点击chiclet的查看按钮可以打开小工具
    Given 用户打开首页
    When 用户点击Swagger chiclet上的Cucumber的查看按钮
    Then 用户可以打开Cucumber转Excel工具

  Scenario: 从导航栏上点击Cucumber转Excel可以打开小工具
    Given 用户打开首页
    When 用户点击导航栏上的Cucumber转Excel按钮
    Then 用户可以打开Cucumber转Excel工具