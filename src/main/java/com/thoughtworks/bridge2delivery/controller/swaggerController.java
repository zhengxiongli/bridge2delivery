package com.thoughtworks.bridge2delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.io.*;

@Controller
@RequestMapping("/swagger")
public class swaggerController {

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String upload(@RequestParam("swaggerFile")  MultipartFile file, HttpServletResponse response) throws FileNotFoundException {
        System.out.println(file);
        if(file.isEmpty()) {
            return "文件不能为空";
        }
        this.downloadWord(response);
        return "上传失败";
    }

    private void downloadWord(HttpServletResponse response) throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:static/test.docx");
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName() );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
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
