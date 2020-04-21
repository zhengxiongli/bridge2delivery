const { Given, When, Then } = require("cucumber");
const { expect } = require("chai");

Given("用户打开首页", async function () {
    await this.openPage("http://localhost:8080/");
});

When("用户点击Swagger chiclet上的Swagger的查看按钮", async function () {
    const VIEW_BTN_SELECTOR = "div.item.swagger a.link";
    await this.page.waitForSelector(VIEW_BTN_SELECTOR);
    await this.page.click(VIEW_BTN_SELECTOR);
});

Then("用户可以打开Swagger转Doc工具", async function () {
    const HEADING_SELECTOR = "h2.title.swagger-title";
    await this.page.waitForSelector(HEADING_SELECTOR);
    const HEADING_ELEMENT = await this.page.$(HEADING_SELECTOR);
    const HEADING_TEXT = await this.page.evaluate(HEADING_ELEMENT => HEADING_ELEMENT.textContent, HEADING_ELEMENT);
    expect(HEADING_TEXT.trim()).to.equal("Swagger转Word");
});

When("用户点击导航栏上的Swagger转Word按钮", async function () {
    const BTN_SELECTOR = "header.app-header ul.nav li:nth-child(1) a";
    await this.page.waitForSelector(BTN_SELECTOR);
    await this.page.click(BTN_SELECTOR);
});