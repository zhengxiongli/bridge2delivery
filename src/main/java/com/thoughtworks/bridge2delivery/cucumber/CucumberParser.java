package com.thoughtworks.bridge2delivery.cucumber;

import com.thoughtworks.bridge2delivery.exception.CustomException;
import io.cucumber.core.feature.FeatureParser;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.core.resource.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static com.thoughtworks.bridge2delivery.contents.Messages.FILE_CAN_NOT_BE_NULL;
import static com.thoughtworks.bridge2delivery.contents.Messages.PARSE_ERROR;

public final class CucumberParser {
    private CucumberParser() {
    }

    public static Feature parse(MultipartFile featureFile) {
        if (featureFile.isEmpty()) {
            throw new CustomException(FILE_CAN_NOT_BE_NULL);
        }

        FeatureParser parser = new FeatureParser(UUID::randomUUID);

        try {
            Optional<Feature> feature = parser.parseResource(new Resource() {
                @Override
                public URI getUri() {
                    return URI.create(featureFile.getName());
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return featureFile.getInputStream();
                }
            });
            return feature.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(PARSE_ERROR);
        }
    }
}
