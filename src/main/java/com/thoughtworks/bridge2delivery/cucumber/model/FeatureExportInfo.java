package com.thoughtworks.bridge2delivery.cucumber.model;

import com.thoughtworks.bridge2delivery.template.Template;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeatureExportInfo {
    @Template(order = 0, description = "Feature名")
    private String featureName;
    @Template(order = 1, description = "Feature描述")
    private String featureDescription;
    @Template(order = 2, description = "测试用例")
    private ScenarioInfo scenarioInfo;
}
