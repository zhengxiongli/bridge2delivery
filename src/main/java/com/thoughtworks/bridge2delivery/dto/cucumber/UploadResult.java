package com.thoughtworks.bridge2delivery.dto.cucumber;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UploadResult {
    private List<String> featureFiles;
    private List<String> unrecognizedFeatureFiles;
    private List<String> otherFiles;

    public void addFeatureFile(String fileName) {
        if (featureFiles == null) {
            featureFiles = new ArrayList<>();
        }
        featureFiles.add(fileName);
    }

    public void addUnrecognizedFeatureFile(String fileName) {
        if (unrecognizedFeatureFiles == null) {
            unrecognizedFeatureFiles = new ArrayList<>();
        }
        unrecognizedFeatureFiles.add(fileName);
    }

    public void addOtherFile(String fileName) {
        if (otherFiles == null) {
            otherFiles = new ArrayList<>();
        }
        otherFiles.add(fileName);
    }
}
