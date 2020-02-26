package com.thoughtworks.bridge2delivery.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
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

public final class Utils {
    private static final int MAX_WAIT_TIME = 10 * 1000;
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 600;

    private Utils() {
    }

    public static String getFromUrl(String url) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(MAX_WAIT_TIME);
        requestFactory.setReadTimeout(MAX_WAIT_TIME);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.getForObject(url, String.class);
    }

    public static String getTextFromFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return getTextFromInputStream(inputStream);
        }
    }

    public static String getTextFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = null;
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
            BufferedImage img = renderer.getImage();
            return img;
        }
    }
}
