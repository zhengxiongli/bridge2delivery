package com.thoughtworks.bridge2delivery.controller;

import com.thoughtworks.bridge2delivery.contents.SessionAttributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

@SpringBootTest
class CucumberControllerTest {
    @Autowired
    private CucumberController cucumberController;

    @Test
    public void should_parse_json_and_set_into_session() throws IOException {
        // given
        Path filePath = Paths.get("src", "test", "resources", "features", "user-creation.feature");
        MockMultipartFile file = new MockMultipartFile("fileName", Files.readAllBytes(filePath));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockMultipartFile[] files = new MockMultipartFile[1];
        files[0] = file;

        // when
        cucumberController.uploadFeatureFiles(files, request);

        // then
        Assertions.assertNotNull(Objects.requireNonNull(request.getSession())
                .getAttribute(SessionAttributes.CUCUMBER_INFO));
    }

}