package com.thoughtworks.bridge2delivery.cucumber;

import com.thoughtworks.bridge2delivery.cucumber.model.CustomPickle;
import com.thoughtworks.bridge2delivery.cucumber.model.FeatureInfo;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.core.gherkin.FeatureParser;
import io.cucumber.core.gherkin.FeatureParserException;
import io.cucumber.core.gherkin.Pickle;
import io.cucumber.core.internal.gherkin.AstBuilder;
import io.cucumber.core.internal.gherkin.GherkinDialect;
import io.cucumber.core.internal.gherkin.GherkinDialectProvider;
import io.cucumber.core.internal.gherkin.Parser;
import io.cucumber.core.internal.gherkin.ParserException;
import io.cucumber.core.internal.gherkin.TokenMatcher;
import io.cucumber.core.internal.gherkin.ast.GherkinDocument;
import io.cucumber.core.internal.gherkin.pickles.Compiler;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CustomFeatureParser implements FeatureParser {

    private static Optional<Feature> parseGherkin6(URI path, String source) {
        try {
            Parser<GherkinDocument> parser = new Parser<>(new AstBuilder());
            TokenMatcher matcher = new TokenMatcher();
            GherkinDocument gherkinDocument = parser.parse(source, matcher);
            if (gherkinDocument.getFeature() == null) {
                return Optional.empty();
            }
            List<Pickle> pickles = compilePickles(path, gherkinDocument);
            if (pickles.isEmpty()) {
                return Optional.empty();
            }
            FeatureInfo feature = new FeatureInfo(gherkinDocument, path, source, pickles);
            return Optional.of(feature);
        } catch (ParserException e) {
            throw new FeatureParserException("Failed to parse resource at: " + path.toString(), e);
        }
    }

    private static List<Pickle> compilePickles(URI path, GherkinDocument document) {
        GherkinDialectProvider dialectProvider = new GherkinDialectProvider();
        String language = document.getFeature().getLanguage();
        GherkinDialect dialect = dialectProvider.getDialect(language, null);
        return new Compiler().compile(document)
                .stream()
                .map(pickle -> new CustomPickle(pickle, path, document, dialect))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Feature> parse(URI path, String source, Supplier<UUID> idGenerator) {
        return parseGherkin6(path, source);
    }

    @Override
    public String version() {
        return "6";
    }
}
