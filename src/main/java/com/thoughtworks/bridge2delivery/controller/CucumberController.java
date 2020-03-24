package com.thoughtworks.bridge2delivery.controller;

import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.contents.SessionAttributes;
import com.thoughtworks.bridge2delivery.cucumber.CucumberParser;
import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import com.thoughtworks.bridge2delivery.dto.cucumber.UploadResult;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.service.ThymeleafService;
import com.thoughtworks.bridge2delivery.utils.Utils;
import io.cucumber.core.gherkin.Feature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cucumber")
@Slf4j
@Api(tags = {"Cucumber"})
public class CucumberController {

    private static final String DEFAULT_TEMPLATE_CLASSPATH = "static/template/cucumber-default-template.html";
    private final ThymeleafService thymeleafService;

    public CucumberController(final ThymeleafService thymeleafService) {
        this.thymeleafService = thymeleafService;
    }

    @PostMapping("/feature")
    @ApiOperation(value = "上传feature文件")
    public ApiResponse<UploadResult> uploadFeatureFiles(@RequestParam("file") MultipartFile[] files,
                                                        HttpServletRequest request) {
        List<Feature> featureList = new ArrayList<>(files.length);
        UploadResult uploadResult = new UploadResult();
        for (MultipartFile file : files) {
            Feature feature = CucumberParser.parse(file).orElseThrow(null);
            if (feature != null) {
                uploadResult.addFeatureFile(file.getOriginalFilename());
                featureList.add(feature);
            } else {
                uploadResult.addOtherFile(file.getOriginalFilename());
            }
        }
        request.getSession().setAttribute(SessionAttributes.CUCUMBER_INFO, featureList);
        return ApiResponse.ok(uploadResult);
    }

    @PostMapping("/template")
    @ApiOperation(value = "上传模板")
    public ApiResponse<?> uploadTemplate(@RequestParam("param") MultipartFile multipartFile) {
        return ApiResponse.ok();
    }

    @PutMapping("/default/template")
    @ApiOperation(value = "恢复为默认模板")
    public ApiResponse<?> resetToDefault() {
        return ApiResponse.ok();
    }

    @GetMapping("/excel")
    @ApiOperation(value = "导出Excel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".xls";
            response.setHeader("Content-disposition", "attachment;filename=cucumber-" +
                    URLEncoder.encode(fileName, "utf-8"));
            byte[] bytes = getFinallyHtml(request).getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }

    @GetMapping("/html")
    @ApiOperation(value = "html预览", produces = MediaType.TEXT_HTML_VALUE)
    public String htmlPreview(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        return "html";
    }

    private String getFinallyHtml(HttpServletRequest request) {
        try {
            ClassPathResource resource = new ClassPathResource(DEFAULT_TEMPLATE_CLASSPATH);
            try (InputStream inputStream = resource.getInputStream()) {
                return Utils.getTextFromInputStream(inputStream);
            }
        } catch (IOException e) {
            throw new CustomException(Messages.TEMPLATE_FILE_NOT_FOUND);
        }
    }

    private String getDefaultTemplate() {
        try {
            ClassPathResource resource = new ClassPathResource(DEFAULT_TEMPLATE_CLASSPATH);
            try (InputStream inputStream = resource.getInputStream()) {
                return Utils.getTextFromInputStream(inputStream);
            }
        } catch (IOException e) {
            throw new CustomException(Messages.TEMPLATE_FILE_NOT_FOUND);
        }
    }

}
