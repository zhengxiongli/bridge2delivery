const { setWorldConstructor } = require("cucumber");
const assert = require("assert");
const puppeteer = require("puppeteer");
const fs = require('fs');

class World {
    constructor({ attach, parameters }) {
        this.attach = attach;
        this.parameters = parameters;
        this.tmpDir = "./tmp"
    }

    async launch(isHeadless) {
        this.browser = await puppeteer.launch({"headless": isHeadless});
        this.page = await this.browser.newPage();
        await this.page.setViewport({
            width: 1280,
            height: 800
        })
    }

    async openPage(url) {
        await this.page.goto(url);
    }

    async takeScreenshot() {
        const img = await this.page.screenshot({ encoding: "base64" });
        return this.attach(img, "image/png");
    }

    async closePage() {
        await this.browser.close();
    }

    async setDownloadDir(path) {
        await this.page._client.send('Page.setDownloadBehavior', {
            behavior: 'allow',
            downloadPath: path
        });
    }

    async removeDir(path) {
        var files = [];
        if (fs.existsSync(path)) {
            files = fs.readdirSync(path);
            files.forEach(function (file, index) {
                var curPath = path + "/" + file;
                if (fs.statSync(curPath).isDirectory()) { // recurse
                    deleteall(curPath);
                } else { // delete file
                    fs.unlinkSync(curPath);
                }
            });
            fs.rmdirSync(path);
        }
    };

    getTimeString() {
        const DATE_OBJECT = new Date();
        const MONTH = ("0" + (DATE_OBJECT.getMonth() + 1)).slice(-2);
        const DATE = ("0" + DATE_OBJECT.getDate()).slice(-2);
        const TIMESTRING = DATE_OBJECT.getFullYear() + MONTH + DATE;
        return TIMESTRING;
    }
}

setWorldConstructor(World);