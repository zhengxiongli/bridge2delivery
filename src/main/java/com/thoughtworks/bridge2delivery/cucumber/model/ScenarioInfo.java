package com.thoughtworks.bridge2delivery.cucumber.model;

import com.thoughtworks.bridge2delivery.template.Template;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScenarioInfo {
    @Template(order = 0, description = "编号")
    private int caseNumber;
    @Template(order = 1, description = "用例名")
    private String name;
    @Template(order = 2, description = "前置条件")
    private String given;
    @Template(order = 3, description = "步骤")
    private String when;
    @Template(order = 4, description = "期待结果")
    private String then;
    @Template(order = 5, description = "描述")
    private String description;
}
