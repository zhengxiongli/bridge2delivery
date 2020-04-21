package com.thoughtworks.bridge2delivery.cucumber.model;

import com.thoughtworks.bridge2delivery.template.Template;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CucumberInfo {
    @Template(order = 0, description = "features")
    private List<FeatureExportInfo> features;
}
