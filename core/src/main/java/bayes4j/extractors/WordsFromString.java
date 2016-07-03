package bayes4j.extractors;

import java.util.Collection;

import bayes4j.Extractor;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 * An extractor that returns the collection of words found in the given text.
 */
public final class WordsFromString implements Extractor<String, String> {
    private static final long serialVersionUID = -586362721397797884L;

    @Override
    public Collection<String> process(String text) {
        text = requireNonNull(text).trim();
        if (text.isEmpty()) {
            return emptyList();
        }
        return asList(text.split("\\P{LD}+"));
    }
}
