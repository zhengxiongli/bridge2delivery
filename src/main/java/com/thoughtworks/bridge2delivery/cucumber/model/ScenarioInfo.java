package com.thoughtworks.bridge2delivery.cucumber.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScenarioInfo {
    private int caseNumber;
    private String name;
    private String description;
    private String given;
    private String when;
    private String then;
}
