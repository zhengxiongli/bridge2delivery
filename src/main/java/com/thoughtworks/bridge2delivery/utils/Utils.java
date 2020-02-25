package com.thoughtworks.bridge2delivery.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Utils {

    private Utils() {
    }

    public static String getFromUrl(String url) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10*1000);
        requestFactory.setReadTimeout(10*1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.getForObject(url, String.class);
    }

    public static String getTextFromFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return getTextFromInputStream(inputStream);
        }
    }

    private static String getTextFromInputStream(InputStream inputStream) throws IOException {
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
}
