package com.thoughtworks.bridge2delivery.cucumber.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CucumberExportInfo {
    private String featureDescription;
    private String featureName;
    private int caseNumber;
    private String name;
    private String description;
    private String given;
    private String when;
    private String then;
    private String featureApproval;
}
