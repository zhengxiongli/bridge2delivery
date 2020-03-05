package com.thoughtworks.bridge2delivery.controller;

import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.template.TemplateInfo;
import com.thoughtworks.bridge2delivery.template.TemplateManager;
import com.thoughtworks.bridge2delivery.template.TemplateType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@Api(description = "自定义模板")
@RequestMapping("/template")
public class TemplateController {

    private static final String FILE_NAME = "Swagger转doc模板_%s.html";

    @ApiOperation(value = "获取配置对象")
    @GetMapping("/config")
    public ApiResponse<TemplateInfo> getConfig(@RequestParam("type") TemplateType type) {
        TemplateInfo templateInfo = TemplateManager.getTemplateInfo(type);
        if (templateInfo == null) {
            throw new CustomException(Messages.UNREALIZED);
        }
        return ApiResponse.ok(templateInfo);
    }

    @ApiOperation(value = "生成template")
    @PostMapping("result")
    public void generate(@RequestParam("type") TemplateType type, @RequestParam("template") String template,
                                HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = String.format(FILE_NAME, LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
            response.setHeader("Content-disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName, "utf-8"));
            byte[] bytes = {};
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }
}
