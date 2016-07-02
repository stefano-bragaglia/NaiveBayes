package api;

import java.io.Serializable;
import java.util.Collection;

/**
 * Implement this class to build a {@link Feature}, a component that takes a {@link Sample} and returns
 * the collection of {@link Feature}s that it contains. The {@link Feature}s will eventually be fed into a
 * <i>Naive Bayes classifier</i>.
 *
 * @param <Sample>  the entity that contains the {@link Feature}s used for classification
 * @param <Feature> one of the distinctive characteristic of the {@link Sample}
 */
public interface Finder<Sample, Feature> extends Serializable {
    /**
     * Processes a {@link Sample} and returns the collection of {@link Feature}s that it contains.
     *
     * @param sample the {@link Sample} to process
     *
     * @return the collection of {@link Feature}s that are contained in the {@link Sample}
     */
    Collection<Feature> process(Sample sample);
}
