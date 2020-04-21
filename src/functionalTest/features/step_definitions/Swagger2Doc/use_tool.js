const { Given, When, Then } = require("cucumber");
const chai = require("chai");
const { expect } = require("chai");
chai.use(require("chai-fs"));

Given("用户打开SWagger转Doc工具", async function () {
    await this.openPage("http://localhost:8080/swagger");
});

When("用户在URL栏输入{string}", async function (url) {
    const INPUT_SELECTOR = "section.swagger-container input.url-input";
    await this.page.waitForSelector(INPUT_SELECTOR);
    const INPUT_ELEMENT = await this.page.$(INPUT_SELECTOR);
    await INPUT_ELEMENT.type(url);
});

When("用户点击解析", async function () {
    const PARSE_SELECTOR = "section.swagger-container button.analysis";
    await this.page.waitForSelector(PARSE_SELECTOR);
    await this.page.click(PARSE_SELECTOR);
});

Then("用户可以看到预览", async function () {
    const PREVIEW_SELECTOR = "div.preview";
    expect(await this.page.waitForSelector(PREVIEW_SELECTOR, { visible: true}));
});

Given("用户看到了{string}的预览", async function (url) {
    await this.openPage("http://localhost:8080/swagger");
    const INPUT_SELECTOR = "section.swagger-container input.url-input";
    await this.page.waitForSelector(INPUT_SELECTOR);
    const INPUT_ELEMENT = await this.page.$(INPUT_SELECTOR);
    await INPUT_ELEMENT.type(url);
    const PARSE_SELECTOR = "section.swagger-container button.analysis";
    await this.page.waitForSelector(PARSE_SELECTOR);
    await this.page.click(PARSE_SELECTOR);
    const PREVIEW_SELECTOR = "div.preview";
    await this.page.waitForSelector(PREVIEW_SELECTOR, { visible: true});
});

When("用户点击生成", async function () {
    const GENERATE_SELECTOR = "div.preview div.preview-head div.preview-btns a.btn.generate";
    await this.page.waitForSelector(GENERATE_SELECTOR);
    await this.page.click(GENERATE_SELECTOR);
    await this.page.waitFor(1000);
});

Then("用户可以得到一个Word文件", { timeout: 2 * 5000 }, async function () {
    const TIMESTRING = this.getTimeString();
    expect(this.tmpDir + "/swagger-" + TIMESTRING + ".doc").to.be.a.file("File not exist");
});

When("用户更换专为功能测试使用的Swagger模板", async function () {
    const CHANGE_TEMPLATE_SELECTOR = "div.preview div.preview-head div.preview-btns input[type = 'file']";
    const INPUT_ELEM = await this.page.waitForSelector(CHANGE_TEMPLATE_SELECTOR);
    await INPUT_ELEM.uploadFile("./resources/Template_Swagger2Doc_FT.html");
    
    // 无法上传文件，本case暂时搁置
    return "pending";
});

Then("用户可以在预览中看到{string}字样", async function (label) {
    var frames = await this.page.frames();
    const PREVIEW_FRAME = await frames.find(f => f.url().indexOf("swagger/html") > -1);
    expect(await PREVIEW_FRAME.evaluate(() => document.querySelector('h1').textContent)).to.have.string(label);
});

