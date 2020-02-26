package com.thoughtworks.bridge2delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.contents.SessionAttributes;
import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.swagger.SwaggerUtils;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;
import com.thoughtworks.bridge2delivery.utils.ThymeleafUtils;
import com.thoughtworks.bridge2delivery.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/swagger")
@Slf4j
@Api(description = "swagger导出")
public class SwaggerController {
    private static final int BUFF_SIZE = 1024;
    private final ThymeleafUtils thymeleafUtils;

    public SwaggerController(final ThymeleafUtils thymeleafUtils) {
        this.thymeleafUtils = thymeleafUtils;
    }

    @PostMapping(value = "/json")
    @ApiOperation(value = "上传swagger文件")
    public ApiResponse upload(@RequestParam("swaggerFile") MultipartFile file, HttpServletRequest request)
            throws IOException {
        if (file.isEmpty()) {
            throw new CustomException(Messages.FILE_CAN_NOT_BE_NULL);
        }
        SwaggerInfo swaggerInfo = SwaggerUtils.parseSwaggerJson(Utils.getTextFromFile(file));
        request.getSession().setAttribute(SessionAttributes.SWAGGER_INFO, swaggerInfo);
        return ApiResponse.ok(null);
    }

    @PostMapping(value = "/url")
    @ApiOperation(value = "设置swagger url")
    public ApiResponse setUrl(@RequestParam("url") String url, HttpServletRequest request)
            throws JsonProcessingException {
        SwaggerInfo swaggerInfo = SwaggerUtils.parseSwaggerJson(Utils.getFromUrl(url));
        request.getSession().setAttribute(SessionAttributes.SWAGGER_INFO, swaggerInfo);
        return ApiResponse.ok(null);
    }

    @PostMapping(value = "/template")
    @ApiOperation(value = "上传模板")
    public ApiResponse uploadTemplate(@RequestParam("swaggerFile")  MultipartFile file, HttpServletRequest request)
            throws IOException {
        if (file.isEmpty()) {
            throw new CustomException(Messages.FILE_CAN_NOT_BE_NULL);
        }
        String template = Utils.getTextFromFile(file);
        request.getSession().setAttribute(SessionAttributes.SWAGGER_TEMPLATE, template);
        return ApiResponse.ok(null);
    }

    @PostMapping(value = "/default/template")
    @ApiOperation(value = "恢复为默认模板")
    public ApiResponse resetToDefault(HttpServletRequest request) {
        request.getSession().setAttribute(SessionAttributes.SWAGGER_TEMPLATE, getDefaultTemplate());
        return ApiResponse.ok(null);
    }

    @GetMapping(value = "word")
    @ApiOperation(value = "导出word文档")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".doc";
            response.setHeader("Content-disposition", "attachment;filename=swagger-" +
                    URLEncoder.encode(fileName, "utf-8"));
            byte[] bytes = getFinnalyHtml(request).getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }

    @GetMapping(value = "/image")
    @ApiOperation(value = "图片预览")
    public void imagePreview(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParserConfigurationException, SAXException {
        String html = getFinnalyHtml(request);
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename=preview.jpeg");

        ImageIO.write(Utils.html2Image(html), "jpg", response.getOutputStream());
    }

    @GetMapping(value = "/html")
    @ApiOperation(value = "html预览")
    public ApiResponse<String> htmlPreview(HttpServletRequest request) {
        return ApiResponse.ok(getFinnalyHtml(request));
    }

    private String getFinnalyHtml(HttpServletRequest request) {
        SwaggerInfo swaggerInfo = (SwaggerInfo) request.getSession().getAttribute(SessionAttributes.SWAGGER_INFO);
        String template = (String) request.getSession().getAttribute(SessionAttributes.SWAGGER_TEMPLATE);
        if (swaggerInfo == null) {
            throw new CustomException(Messages.UPLOAD_SWAGGER_JSON);
        }
        if (StringUtils.isEmpty(template)) {
            template = getDefaultTemplate();
            request.getSession().setAttribute(SessionAttributes.SWAGGER_TEMPLATE, template);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("swaggerInfo", swaggerInfo);
        String html = thymeleafUtils.renderTemplate(template, map);
        return html;
    }

    private String getDefaultTemplate() {
        try {
            File file = ResourceUtils.getFile("classpath:templates/default.html");
            return getFileText(file);
        } catch (Exception e) {
            log.error("load swagger default template error", e);
            throw new CustomException(Messages.UPLOAD_SWAGGER_TEMPLATE);
        }
    }

    private String getFileText(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int len = 0;
            byte[] bytes = new byte[BUFF_SIZE];
            while ((len = inputStream.read(bytes)) > -1) {
                builder.append(new String(bytes, 0, len));
            }
        }
        return builder.toString();
    }
}
