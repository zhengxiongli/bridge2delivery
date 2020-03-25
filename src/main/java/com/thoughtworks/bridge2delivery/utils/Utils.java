package com.thoughtworks.bridge2delivery.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class Utils {
    private static final int MAX_WAIT_TIME = 10 * 1000;
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 1000;
    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(MAX_WAIT_TIME);
        requestFactory.setReadTimeout(MAX_WAIT_TIME);
        restTemplate = new RestTemplate(requestFactory);
    }

    private Utils() {
    }

    public static String getFromUrl(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().contains("sockettimeoutexception")) {
                throw new CustomException(Messages.REQUEST_TIMEOUT);
            }
            throw new CustomException(Messages.INVALID_URL);
        }
    }

    public static String getTextFromFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return getTextFromInputStream(inputStream);
        }
    }

    public static String getTextFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        }
        return builder.toString();
    }

    public static BufferedImage html2Image(String html) throws IOException, ParserConfigurationException, SAXException {
        byte[] bytes = html.getBytes();
        try (ByteArrayInputStream bin = new ByteArrayInputStream(bytes)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(bin);
            Java2DRenderer renderer = new Java2DRenderer(document, IMAGE_WIDTH, IMAGE_HEIGHT);
            return renderer.getImage();
        }
    }

    public static void validateJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new CustomException(Messages.INVALID_JSON, e);
        }
    }

    public static String parseFileExtension(String fileName) {
        if (StringUtils.isEmptyOrWhitespace(fileName)) {
            throw new CustomException(Messages.INVALID_FILE_NAME);
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
