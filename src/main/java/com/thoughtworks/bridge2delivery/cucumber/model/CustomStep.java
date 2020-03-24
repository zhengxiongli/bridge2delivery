package com.thoughtworks.bridge2delivery.cucumber.model;

import io.cucumber.core.gherkin.Argument;
import io.cucumber.core.gherkin.DataTableArgument;
import io.cucumber.core.gherkin.DocStringArgument;
import io.cucumber.core.gherkin.Step;
import io.cucumber.core.gherkin.StepType;
import io.cucumber.core.internal.gherkin.GherkinDialect;
import io.cucumber.core.internal.gherkin.ast.GherkinDocument;
import io.cucumber.core.internal.gherkin.pickles.PickleStep;
import io.cucumber.core.internal.gherkin.pickles.PickleString;
import io.cucumber.core.internal.gherkin.pickles.PickleTable;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomStep implements Step {
    private final PickleStep step;
    private final Argument argument;
    private final String keyWord;
    private final StepType stepType;
    private final String previousGwtKeyWord;
    private final String uri;

    public CustomStep(PickleStep step, GherkinDocument document, GherkinDialect dialect, String previousGwtKeyWord, String uri) {
        this.step = step;
        this.argument = extractArgument(step, getLine());
        this.keyWord = extractKeyWord(document, getLine());
        this.stepType = extractKeyWordType(keyWord, dialect);
        this.previousGwtKeyWord = previousGwtKeyWord;
        this.uri = uri;
    }

    private static String extractKeyWord(GherkinDocument document, int stepLine) {
        return document.getFeature().getChildren().stream()
                .flatMap(scenarioDefinition -> scenarioDefinition.getSteps().stream())
                .filter(step -> step.getLocation().getLine() == stepLine)
                .findFirst()
                .map(io.cucumber.core.internal.gherkin.ast.Step::getKeyword)
                .orElseThrow(() -> new IllegalStateException("GherkinDocument did not contain PickleStep"));
    }

    private static Argument extractArgument(PickleStep pickleStep, int stepLine) {
        if (pickleStep.getArgument().isEmpty()) {
            return null;
        }
        io.cucumber.core.internal.gherkin.pickles.Argument argument = pickleStep.getArgument().get(0);
        if (argument instanceof PickleString) {
            PickleString docString = (PickleString) argument;
            return getDocStringArgument(docString);
        }
        if (argument instanceof PickleTable) {
            PickleTable table = (PickleTable) argument;
            return getDataTableArgument(stepLine, table);
        }
        return null;
    }

    private static DocStringArgument getDocStringArgument(PickleString docString) {
        return new DocStringArgument() {
            @Override
            public String getContent() {
                return docString.getContent();
            }

            @Override
            public String getContentType() {
                return docString.getContentType();
            }

            @Override
            public int getLine() {
                return docString.getLocation().getLine();
            }
        };
    }

    private static DataTableArgument getDataTableArgument(int stepLine, PickleTable table) {
        return new DataTableArgument() {
            @Override
            public List<List<String>> cells() {
                return new CellView(table);
            }

            @Override
            public int getLine() {
                if (table.getRows().size() > 0 && table.getRows().get(0).getCells().size() > 0) {
                    return table.getLocation().getLine();
                } else {
                    return stepLine + 1;
                }
            }

            class CellView extends AbstractList<List<String>> {
                private final PickleTable table;

                CellView(PickleTable table) {
                    this.table = table;
                }

                @Override
                public List<String> get(int row) {
                    return new AbstractList<String>() {
                        @Override
                        public String get(int column) {
                            return table.getRows().get(row).getCells().get(column).getValue();
                        }

                        @Override
                        public int size() {
                            return table.getRows().get(row).getCells().size();
                        }
                    };
                }

                @Override
                public int size() {
                    return table.getRows().size();
                }
            }
        };
    }

    private StepType extractKeyWordType(String keyWord, GherkinDialect dialect) {
        if (StepType.isAstrix(keyWord)) {
            return StepType.OTHER;
        }
        if (dialect.getGivenKeywords().contains(keyWord)) {
            return StepType.GIVEN;
        }
        if (dialect.getWhenKeywords().contains(keyWord)) {
            return StepType.WHEN;
        }
        if (dialect.getThenKeywords().contains(keyWord)) {
            return StepType.THEN;
        }
        if (dialect.getAndKeywords().contains(keyWord)) {
            return StepType.AND;
        }
        if (dialect.getButKeywords().contains(keyWord)) {
            return StepType.BUT;
        }
        throw new IllegalStateException("Keyword " + keyWord + " was neither given, when, then, and, but nor *");
    }

    @Override
    public int getLine() {
        int last = step.getLocations().size() - 1;
        return step.getLocations().get(last).getLine();
    }

    @Override
    public Argument getArgument() {
        return argument;
    }

    @Override
    public String getKeyWord() {
        return keyWord;
    }

    @Override
    public StepType getType() {
        return stepType;
    }

    @Override
    public String getPreviousGivenWhenThenKeyWord() {
        return previousGwtKeyWord;
    }

    @Override
    public String getText() {
        return step.getText();
    }

    @Override
    public String getId() {
        String lineNumbers = this.step.getLocations().stream()
                .map(s -> String.valueOf(s.getLine()))
                .collect(Collectors.joining(":"));
        return uri + ":" + lineNumbers;
    }
}
