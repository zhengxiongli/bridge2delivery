const { Given, When, Then } = require('cucumber')
const { expect } = require('chai');

When("用户点击创建新模板工具的Cucumber按钮", async function () {
    const BTN_SELECTOR = "main.app-main div.category div:nth-child(2) a";
    await this.page.waitForSelector(BTN_SELECTOR);
    await this.page.click(BTN_SELECTOR);
});

Then("用户可以看到编辑Cucumber模板的页面", async function () {
    const TREE_SELECTOR = "div.template-tree-wrapper";
    const TREE = await this.page.waitForSelector(TREE_SELECTOR);
    expect(TREE).to.be.not.undefined;
});

Given("用户打开编辑Cucumber模板的页面", async function () {
    await this.openPage("http://localhost:8080/template?type=CUCUMBER");
});

Then("用户可以下载Cucumber模板", async function () {
    const TIMESTRING = this.getTimeString();
    expect(this.tmpDir + "/Cucumber转Excel模板_" + TIMESTRING + ".html").to.be.a.file("File not exist");
});
