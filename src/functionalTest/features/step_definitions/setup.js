const { Before, After } = require("cucumber");

Before(async function () {
    await this.launch(isHeadless = false);
    await this.setDownloadDir(this.tmpDir);
});

After(async function () {
    await this.takeScreenshot();
    await this.removeDir(this.tmpDir);
    return await this.closePage();
})
