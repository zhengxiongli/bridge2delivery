const { Given, When, Then } = require('cucumber')
const { expect } = require("chai");


When("用户点击导航栏上的创建新模版按钮", async function () {
    const BTN_SELECTOR = "header.app-header ul.nav li:nth-child(3) a";
    await this.page.waitForSelector(BTN_SELECTOR);
    await this.page.click(BTN_SELECTOR);
});

Then("用户可以打开创建新模板工具", async function () {
    const HEADING_SELECTOR = "main.app-main div.content h2";
    await this.page.waitForSelector(HEADING_SELECTOR);
    const HEADING_ELEMENT = await this.page.$(HEADING_SELECTOR);
    const HEADING_TEXT = await this.page.evaluate(HEADING_ELEMENT => HEADING_ELEMENT.textContent, HEADING_ELEMENT);
    expect(HEADING_TEXT.trim()).to.equal("制作模版");
});

Given("用户打开创建新模板工具", async function () {
    await this.openPage("http://localhost:8080/template-portal");
});

When("用户点击创建新模板工具的Swagger按钮", async function () {
    const BTN_SELECTOR = "main.app-main div.category div:nth-child(1) a";
    await this.page.waitForSelector(BTN_SELECTOR);
    await this.page.click(BTN_SELECTOR);
});

Then("用户可以看到编辑Swagger模板的页面", async function () {
    const TREE_SELECTOR = "div.template-container div.template-tree-node";
    const TREE = await this.page.waitForSelector(TREE_SELECTOR);
    expect(TREE).to.be.not.undefined;
});

Given("用户打开编辑Swagger模板的页面", async function () {
    await this.openPage("http://localhost:8080/template?type=SWAGGER");
});

Given("用户在使用说明框点击确定", async function () {
    const BTN_SELECTOR = "div.modal-container  div.modal-buttons div.right button";
    await this.page.waitForSelector(BTN_SELECTOR);
    await this.page.click(BTN_SELECTOR);
});

When("用户点击模板自定义的生成按钮", async function () {
    const BTN_SELECTOR = "div.template-generate-btn-wrapper button.template-generate-btn";
    await this.page.waitForSelector(BTN_SELECTOR);
    await this.setDownloadDir(this.tmpDir);
    await this.page.click(BTN_SELECTOR);
    await this.page.waitFor(1000);
});

Then("用户可以下载Swagger模板", async function () {
    const TIMESTRING = this.getTimeString();
    expect(this.tmpDir + "/Swagger转doc模板_" + TIMESTRING + ".html").to.be.a.file("File not exist");
});
