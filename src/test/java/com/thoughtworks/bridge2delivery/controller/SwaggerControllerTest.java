package com.thoughtworks.bridge2delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.contents.SessionAttributes;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import io.cucumber.core.feature.FeatureParser;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.core.resource.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
class SwaggerControllerTest {
    @Autowired
    private SwaggerController controller;

    @Test
    public void should_thrown_exception_when_upload_file_is_empty() {
        // given
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        // when

        // then
        Assertions.assertThrows(CustomException.class, () -> controller.upload(file, null),
                Messages.FILE_CAN_NOT_BE_NULL);
    }

    @Test
    public void should_parse_json_and_set_into_session() throws IOException {
        // given
        Path filePath = Paths.get("src", "test", "resources", "swagger-info.json");
        MockMultipartFile file = new MockMultipartFile("swaggerFile", Files.readAllBytes(filePath));
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        controller.upload(file, request);

        // then
        Assertions.assertNotNull(Objects.requireNonNull(request.getSession())
                .getAttribute(SessionAttributes.SWAGGER_INFO));
    }

    @Test
    public void should_upload_template_success() throws IOException {
        // given
        Path filePath = Paths.get("src", "test", "resources", "swagger-template.html");
        MockMultipartFile file = new MockMultipartFile("swaggerFile", Files.readAllBytes(filePath));
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        controller.uploadTemplate(file, request);

        // then
        Assertions.assertNotNull(Objects.requireNonNull(request.getSession())
                .getAttribute(SessionAttributes.SWAGGER_TEMPLATE));
    }

    @Test
    public void should_upload_template_success_and_remove_js_code() throws IOException {
        // given
        Path filePath = Paths.get("src", "test", "resources", "swagger-template-js.html");
        MockMultipartFile file = new MockMultipartFile("swaggerFile", Files.readAllBytes(filePath));
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        controller.uploadTemplate(file, request);

        // then
        String template = (String) Objects.requireNonNull(request.getSession())
                .getAttribute(SessionAttributes.SWAGGER_TEMPLATE);
        Assertions.assertFalse(template.contains("<script>"));
    }

    @Test
    public void should_preview_successful() throws IOException {
        // given
        Path filePath = Paths.get("src", "test", "resources", "swagger-info.json");
        MockMultipartFile file = new MockMultipartFile("swaggerFile", Files.readAllBytes(filePath));
        MockHttpServletRequest request = new MockHttpServletRequest();
        controller.upload(file, request);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        String content = controller.htmlPreview(request, response);

        // then
        Assertions.assertTrue(content.contains("嘀哒验收小工具swagger测试程序"));
    }

    @Test
    public void test_parse_feature_file() {
        Path filePath = Paths.get("src", "test", "resources", "features", "user-creation.feature");
        FeatureParser parser = new FeatureParser(UUID::randomUUID);
        Optional<Feature> feature = parser.parseResource(new Resource() {
            @Override
            public URI getUri() {
                return filePath.toUri();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return Files.newInputStream(filePath);
            }
        });
        feature.ifPresent(f -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String s = mapper.writeValueAsString(f);
                System.out.println(s);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}