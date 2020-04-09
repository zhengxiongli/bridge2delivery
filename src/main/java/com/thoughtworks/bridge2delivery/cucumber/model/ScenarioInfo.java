package com.thoughtworks.bridge2delivery.cucumber.model;

import com.thoughtworks.bridge2delivery.template.Template;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScenarioInfo {
    @Template(order = 0, description = "Scenario编号")
    private int scenarioNumber;
    @Template(order = 1, description = "Scenario名")
    private String scenarioName;
    @Template(order = 2, description = "前置条件")
    private String given;
    @Template(order = 3, description = "步骤")
    private String when;
    @Template(order = 4, description = "期待结果")
    private String then;
    @Template(order = 5, description = "Scenario描述")
    private String scenarioDescription;
}
