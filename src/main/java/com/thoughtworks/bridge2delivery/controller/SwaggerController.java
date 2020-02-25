package com.thoughtworks.bridge2delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

//使用@RestController，这样下面就不用标注@ResponseBody
@Controller
@RequestMapping("/swagger")
public class SwaggerController {
    private static final int BUFF_SIZE = 1024;
    //upload在做download的事情，下载和上传可以单独分开，或改下名字
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String upload(@RequestParam("swaggerFile")  MultipartFile file, HttpServletResponse response) throws FileNotFoundException {
        //删除println这种输出，统一使用log.info\log.error\log.debug这种
        System.out.println(file);
        if (file.isEmpty()) {
            //不要直接返回错误信息，统一抛出异常，有统一的异常处理：ExceptionHandler
            return "文件不能为空";
        }
        this.downloadWord(response);
        //下载文件应该是流输出，没有返回值
        return "上传失败";
    }

    private void downloadWord(HttpServletResponse response) throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:static/test.docx");
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[BUFF_SIZE];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
