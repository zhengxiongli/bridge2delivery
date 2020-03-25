package com.thoughtworks.bridge2delivery.cucumber.model;

import io.cucumber.core.gherkin.Feature;
import io.cucumber.core.gherkin.Located;
import io.cucumber.core.gherkin.Location;
import io.cucumber.core.gherkin.Node;
import io.cucumber.core.gherkin.Pickle;
import io.cucumber.core.gherkin.Step;
import io.cucumber.core.gherkin.StepType;
import io.cucumber.core.internal.gherkin.ast.GherkinDocument;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FeatureInfo implements Feature {
    private String name;
    private String description;
    private String approval;
    private List<ScenarioInfo> scenarioInfo;
    private GherkinDocument gherkinDocument;
    private String sourceString;
    private URI uri;

    public FeatureInfo(GherkinDocument gherkinDocument, URI uri, String gherkinSource,
                       List<Pickle> pickles) {
        this.gherkinDocument = gherkinDocument;
        this.uri = uri;
        this.sourceString = gherkinSource;
        this.description =
                StringUtils.trimWhitespace(gherkinDocument.getFeature().getDescription());
        this.name = gherkinDocument.getFeature().getName();
        this.approval = "嘀哒";
        this.scenarioInfo = buildScenario(pickles, gherkinDocument);
    }

    private List<ScenarioInfo> buildScenario(List<Pickle> pickles, GherkinDocument gherkinDocument) {
        AtomicInteger index = new AtomicInteger();

        return pickles.stream()
                .map(pickle -> ScenarioInfo.builder()
                        .name(pickle.getName())
                        .description(
                                gherkinDocument.getFeature().getChildren().stream().filter(child -> Objects.equals(child.getName(), pickle.getName()))
                                        .findFirst().get().getDescription())
                        .caseNumber(index.incrementAndGet())
                        .given(extractStep(pickle.getSteps(), StepType.GIVEN))
                        .when(extractStep(pickle.getSteps(), StepType.WHEN))
                        .then(extractStep(pickle.getSteps(), StepType.THEN))
                        .build())
                .collect(Collectors.toList());
    }

    private String extractStep(List<Step> steps, StepType stepType) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getType().isGivenWhenThen()) {
                indexes.add(i);
            }
        }

        List<Step> targetSteps = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            Integer currentIndex = indexes.get(i);
            if (steps.get(currentIndex).getType() == stepType) {
                if (i == indexes.size() - 1) {
                    targetSteps.addAll(steps.subList(currentIndex, steps.size()));
                    continue;
                }
                targetSteps.addAll(steps.subList(currentIndex, indexes.get(i + 1)));
            }
        }

        return targetSteps.stream()
                .map(Step::getText)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getKeyword() {
        return null;
    }

    @Override
    public Optional<Pickle> getPickleAt(Located located) {
        return Optional.empty();
    }

    @Override
    public List<Pickle> getPickles() {
        return null;
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public String getSource() {
        return sourceString;
    }

    @Override
    public Collection<Node> children() {
        return null;
    }

    @Override
    public Location getLocation() {
        return new Location() {
            @Override
            public int getLine() {
                return gherkinDocument.getLocation().getLine();
            }

            @Override
            public int getColumn() {
                return gherkinDocument.getLocation().getColumn();
            }
        };
    }

    @Override
    public String getKeyWord() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getApproval() {
        return approval;
    }

    public List<ScenarioInfo> getScenarioInfo() {
        return scenarioInfo;
    }

    public void setScenarioInfo(List<ScenarioInfo> scenarioInfo) {
        this.scenarioInfo = scenarioInfo;
    }
}
