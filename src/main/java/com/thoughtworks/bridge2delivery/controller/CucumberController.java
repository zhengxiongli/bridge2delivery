package com.thoughtworks.bridge2delivery.controller;

import com.google.common.collect.Lists;
import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.contents.SessionAttributes;
import com.thoughtworks.bridge2delivery.cucumber.CucumberParser;
import com.thoughtworks.bridge2delivery.cucumber.model.CucumberExportInfo;
import com.thoughtworks.bridge2delivery.cucumber.model.FeatureInfo;
import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import com.thoughtworks.bridge2delivery.dto.cucumber.UploadResult;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.service.ThymeleafService;
import com.thoughtworks.bridge2delivery.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cucumber")
@Slf4j
@Api(tags = {"Cucumber"})
public class CucumberController {
    private static final int PREVIEW_MAX_USE_CASE_10 = 10;
    private static final String DEFAULT_TEMPLATE_CLASSPATH = "static/template/cucumber-default-template.html";
    private final ThymeleafService thymeleafService;

    public CucumberController(final ThymeleafService thymeleafService) {
        this.thymeleafService = thymeleafService;
    }

    @PostMapping("/feature")
    @ApiOperation(value = "上传feature文件")
    public ApiResponse<UploadResult> uploadFeatureFiles(@RequestParam("file") MultipartFile[] files,
                                                        HttpServletRequest request) {
        List<FeatureInfo> featureList = new ArrayList<>(files.length);
        UploadResult uploadResult = new UploadResult();
        Arrays.stream(files).sorted(Comparator.comparing(MultipartFile::getName));
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            FeatureInfo feature = null;
            try {
                feature = (FeatureInfo) CucumberParser.parse(file);
            } catch (CustomException e) {
                log.error("parse error:{}", fileName, e);
            }
            if (feature != null) {
                uploadResult.addFeatureFile(fileName);
                featureList.add(feature);
            } else {
                uploadResult.addUnrecognizedFeatureFile(fileName);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttributes.CUCUMBER_INFO, featureList);
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
    public void downloadExcel(@RequestParam("indexes") String indexes, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        List<Integer> indexList = Arrays.stream(indexes.split(",")).map(Integer::parseInt)
                .collect(Collectors.toList());
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".xls";
            response.setHeader("Content-disposition", "attachment;filename=cucumber-" +
                    URLEncoder.encode(fileName, "utf-8"));
            byte[] bytes = getFinallyHtml(request,  indexList, false).getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }

    @GetMapping("/html")
    @ApiOperation(value = "html预览", produces = MediaType.TEXT_HTML_VALUE)
    public String htmlPreview(@RequestParam("indexes") String indexes, HttpServletRequest request,
                              HttpServletResponse response) {
        List<Integer> indexList = Arrays.stream(indexes.split(",")).map(Integer::parseInt)
                .collect(Collectors.toList());
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        return getFinallyHtml(request, indexList, true);
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

    @SuppressWarnings({"unchecked"})
    private String getFinallyHtml(HttpServletRequest request, List<Integer> indexes, boolean isPreview) {
        List<FeatureInfo> featureInfos = (List<FeatureInfo>) request.getSession()
                .getAttribute(SessionAttributes.CUCUMBER_INFO);
        if (featureInfos.isEmpty()) {
            throw new CustomException(Messages.UPLOAD_FEATURE_FILE);
        }
        List<FeatureInfo> tempFeatureInfos = new ArrayList<>();
        for (Integer index : indexes) {
            tempFeatureInfos.add(featureInfos.get(index));
        }

        List<CucumberExportInfo> cucumberExportInfos = Lists.newArrayList();
        tempFeatureInfos.forEach(featureInfo -> featureInfo.getScenarioInfo()
                .forEach(scenarioInfo -> cucumberExportInfos.add(CucumberExportInfo.builder()
                .caseNumber(scenarioInfo.getCaseNumber())
                .featureName(featureInfo.getName())
                .description(scenarioInfo.getDescription())
                .given(scenarioInfo.getGiven())
                .when(scenarioInfo.getWhen())
                .then(scenarioInfo.getThen())
                .name(scenarioInfo.getName())
                .featureDescription(featureInfo.getDescription())
                .featureApproval(featureInfo.getApproval())
                .build())));

        if (isPreview && cucumberExportInfos.size() > PREVIEW_MAX_USE_CASE_10) {
            return getHtml(request, cucumberExportInfos.subList(0, PREVIEW_MAX_USE_CASE_10));
        }
        return getHtml(request, cucumberExportInfos);
    }

    private String getHtml(HttpServletRequest request, List<CucumberExportInfo> cucumberExportInfos) {
        String template = (String) request.getSession().getAttribute(SessionAttributes.CUCUMBER_TEMPLATE);

        if (StringUtils.isEmpty(template)) {
            template = getDefaultTemplate();
            request.getSession().setAttribute(SessionAttributes.CUCUMBER_TEMPLATE, template);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("cucumberExportInfos", cucumberExportInfos);
        try {
            return thymeleafService.renderTemplate(template, map);
        } catch (Exception e) {
            throw new CustomException(Messages.PARSE_ERROR);
        }
    }
}
