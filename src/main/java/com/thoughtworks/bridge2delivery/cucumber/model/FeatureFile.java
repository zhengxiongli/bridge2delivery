package com.thoughtworks.bridge2delivery.cucumber.model;

import lombok.Data;

@Data
public class FeatureFile {
    private String name;
    private String path;
    private boolean recognized ;
}
