package com.thoughtworks.bridge2delivery.controller;

import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.contents.SessionAttributes;
import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.swagger.SwaggerUtils;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;
import com.thoughtworks.bridge2delivery.utils.ThymeleafUtils;
import com.thoughtworks.bridge2delivery.utils.Utils;
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
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/swagger")
public class SwaggerController {
    private static final String DEFAULT_TEMPLATE_PATH = "classpath:static/template/swagger.html";
    private final ThymeleafUtils thymeleafUtils;

    public SwaggerController(final ThymeleafUtils thymeleafUtils) {
        this.thymeleafUtils = thymeleafUtils;
    }

    @PostMapping(value = "upload")
    public ApiResponse upload(@RequestParam("swaggerFile") MultipartFile file, HttpServletRequest request)
            throws IOException {
        if (file.isEmpty()) {
            throw new CustomException(Messages.FILE_CAN_NOT_BE_NULL);
        }
        SwaggerInfo swaggerInfo = SwaggerUtils.parseSwaggerJson(Utils.getTextFromFile(file));
        log.debug("swagger json: " + swaggerInfo);
        request.getSession().setAttribute(SessionAttributes.SWAGGER_INFO, swaggerInfo);
        return ApiResponse.ok(null);
    }

    @PostMapping(value = "template")
    public ApiResponse uploadTemplate(@RequestParam("swaggerFile") MultipartFile file, HttpServletRequest request)
            throws IOException {
        if (file.isEmpty()) {
            throw new CustomException(Messages.FILE_CAN_NOT_BE_NULL);
        }
        String template = Utils.getTextFromFile(file);
        request.getSession().setAttribute(SessionAttributes.SWAGGER_TEMPLATE, template);
        return ApiResponse.ok(null);
    }

    @GetMapping(value = "doc")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".doc";
            response.setHeader("Content-disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName, "utf-8"));
            byte[] bytes = getFinallyHtml(request).getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }

    @GetMapping(value = "preview")
    public void preview(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParserConfigurationException, SAXException {
        String html = getFinallyHtml(request);
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename=preview.jpeg");

        ImageIO.write(Utils.html2Image(html), "jpg", response.getOutputStream());
    }

    private String getFinallyHtml(HttpServletRequest request) {
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
        try {
            return thymeleafUtils.renderTemplate(template, map);
        }catch (Exception e) {
            throw new CustomException(Messages.PARSE_ERROR);
        }
    }

    private String getDefaultTemplate() {
        try {
            File file = ResourceUtils.getFile(DEFAULT_TEMPLATE_PATH);
            return Utils.getTextFromInputStream(new FileInputStream(file));
        } catch (IOException e) {
            throw new CustomException(Messages.TEMPLATE_FILE_NOT_FOUND);
        }
    }
}
