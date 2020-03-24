package com.thoughtworks.bridge2delivery.cucumber;

import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import io.cucumber.core.gherkin.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CucumberParserTest {

    @Test
    public void should_parse_cucumber_file_successful() throws IOException {
        // given
        Path filePath = Paths.get("src", "test", "resources", "features", "user-creation.feature");
        MockMultipartFile file = new MockMultipartFile("fileName", Files.readAllBytes(filePath));

        // when
        Optional<Feature> parse = CucumberParser.parse(file);

        // then
        assertTrue(parse.isPresent());
        assertEquals(parse.get().getUri().toString(), "fileName");
    }

    @Test
    public void should_thrown_exception_when_upload_file_is_empty() {
        // given
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        // when

        // then
        Assertions.assertThrows(CustomException.class, () -> CucumberParser.parse(file),
                Messages.FILE_CAN_NOT_BE_NULL);
    }
}