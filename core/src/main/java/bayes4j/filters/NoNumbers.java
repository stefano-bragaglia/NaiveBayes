package bayes4j.filters;

import java.util.Collection;

import bayes4j.Filter;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * A filter that removes the numbers from the given collection of features.
 */
public final class NoNumbers implements Filter<String> {
    private static final long serialVersionUID = -5886940524746378882L;

    @Override
    public Collection<String> process(Collection<String> features) {
        return requireNonNull(features).stream()
                                       .filter(feature -> !feature.matches("^\\d+$"))
                                       .collect(toList());
    }
}
