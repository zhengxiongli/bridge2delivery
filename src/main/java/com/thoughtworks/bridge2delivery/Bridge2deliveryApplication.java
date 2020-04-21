package com.thoughtworks.bridge2delivery;

import com.thoughtworks.bridge2delivery.client.DiDaClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Bridge2deliveryApplication {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Bridge2deliveryApplication.class);
        builder.headless(false).run(args);

        new DiDaClient();
    }
}
