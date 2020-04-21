package com.thoughtworks.bridge2delivery.cucumber.model;

import io.cucumber.core.gherkin.Location;
import io.cucumber.core.gherkin.Pickle;
import io.cucumber.core.gherkin.Step;
import io.cucumber.core.gherkin.StepType;
import io.cucumber.core.internal.gherkin.GherkinDialect;
import io.cucumber.core.internal.gherkin.ast.GherkinDocument;
import io.cucumber.core.internal.gherkin.ast.ScenarioDefinition;
import io.cucumber.core.internal.gherkin.pickles.PickleStep;
import io.cucumber.core.internal.gherkin.pickles.PickleTag;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomPickle implements Pickle {
    private final io.cucumber.core.internal.gherkin.pickles.Pickle pickle;
    private final List<Step> steps;
    private final URI uri;
    private final String keyWord;
    private final String id;

    public CustomPickle(io.cucumber.core.internal.gherkin.pickles.Pickle pickle, URI uri,
                        GherkinDocument document, GherkinDialect dialect) {
        this.pickle = pickle;
        this.uri = uri;
        this.steps = createCucumberSteps(pickle, document, dialect, uri.toString());
        this.keyWord = document.getFeature().getChildren().stream()
                .filter((scenarioDefinition) -> scenarioDefinition.getLocation().getLine() == this.getScenarioLocation().getLine())
                .map(ScenarioDefinition::getKeyword).findFirst().orElse("Scenario");
        this.id = pickle.getName() + ":" + pickle.getLocations().stream()
                .map((l) -> String.valueOf(l.getLine())).collect(Collectors.joining(":"));
    }

    private static List<Step> createCucumberSteps(io.cucumber.core.internal.gherkin.pickles.Pickle pickle,
                                                  GherkinDocument document, GherkinDialect dialect, String uri) {
        List<Step> list = new ArrayList<>();
        String previousGivenWhenThen = dialect.getGivenKeywords().stream()
                .filter((s) -> !StepType.isAstrix(s))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Given keyword for dialect: " + dialect.getName()));

        CustomStep cucumberStep;
        for (Iterator stepIterator = pickle.getSteps().iterator(); stepIterator.hasNext(); list.add(cucumberStep)) {
            PickleStep step = (PickleStep) stepIterator.next();
            cucumberStep = new CustomStep(step, document, dialect, previousGivenWhenThen, uri);
            if (cucumberStep.getType().isGivenWhenThen()) {
                previousGivenWhenThen = cucumberStep.getKeyWord();
            }
        }

        return list;
    }

    public String getKeyword() {
        return this.keyWord;
    }

    public String getLanguage() {
        return this.pickle.getLanguage();
    }

    public String getName() {
        return this.pickle.getName();
    }

    public Location getLocation() {
        return new Location() {
            @Override
            public int getLine() {
                return pickle.getLocations().get(0).getLine();
            }

            @Override
            public int getColumn() {
                return pickle.getLocations().get(0).getColumn();
            }
        };
    }

    public Location getScenarioLocation() {
        int last = this.pickle.getLocations().size() - 1;
        return new Location() {
            @Override
            public int getLine() {
                return pickle.getLocations().get(last).getLine();
            }

            @Override
            public int getColumn() {
                return pickle.getLocations().get(last).getColumn();
            }
        };
    }

    public List<Step> getSteps() {
        return this.steps;
    }

    public List<String> getTags() {
        return (List) this.pickle.getTags().stream().map(PickleTag::getName).collect(Collectors.toList());
    }

    public URI getUri() {
        return this.uri;
    }

    public String getId() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            CustomPickle that = (CustomPickle) o;
            return this.id.equals(that.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}
