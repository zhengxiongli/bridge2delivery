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
    private static final String BUT_DESC = "但是";
    private static final String AND_DESC = "并且";
    private String name;
    private String description;
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
        this.scenarioInfo = buildScenario(pickles, gherkinDocument);
    }

    private List<ScenarioInfo> buildScenario(List<Pickle> pickles, GherkinDocument gherkinDocument) {
        AtomicInteger index = new AtomicInteger();

        return pickles.stream()
                .map(pickle -> ScenarioInfo.builder()
                        .scenarioName(pickle.getName())
                        .scenarioDescription(
                                gherkinDocument.getFeature().getChildren().stream().filter(child -> Objects.equals(child.getName(), pickle.getName()))
                                        .findFirst().get().getDescription())
                        .scenarioNumber(index.incrementAndGet())
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

        return getStepInfo(targetSteps);
    }

    private String getStepInfo(List<Step> steps) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < steps.size(); i++) {
            Step step = steps.get(i);
            if (step.getType() == StepType.AND || StepType.isAstrix(step.getKeyWord())) {
                builder.append(AND_DESC);
            } else if (step.getType() == StepType.BUT) {
                builder.append(BUT_DESC);
            } else if (i != 0) {
                builder.replace(builder.length() - 1, builder.length(), "。");
            }
            builder.append(step.getText());
            if (i != steps.size() - 1) {
                builder.append("，");
            }
        }
        return builder.toString();
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

    public List<ScenarioInfo> getScenarioInfo() {
        return scenarioInfo;
    }

    public void setScenarioInfo(List<ScenarioInfo> scenarioInfo) {
        this.scenarioInfo = scenarioInfo;
    }
}
