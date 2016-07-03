package bayes4j.extractors;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import bayes4j.Extractor;

import static java.lang.String.join;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 * An extractor that returns the collection of words found in the text file at a given path.
 */
public final class WordsFromPath implements Extractor<String, Path> {
    private static final long serialVersionUID = 1497425456212620822L;

    @Override
    public Collection<String> process(Path path) {
        try {
            String text = join("\\n", readAllLines(requireNonNull(path))).trim();
            if (text.isEmpty()) {
                return emptyList();
            }
            return asList(text.split("\\P{LD}+"));
        } catch (IOException e) {
            throw new IllegalArgumentException("'path' is invalid: " + path, e);
        }
    }
}
