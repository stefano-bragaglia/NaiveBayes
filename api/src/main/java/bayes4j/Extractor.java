package bayes4j;

import java.io.Serializable;
import java.util.Collection;

/**
 * A component that extracts the collection of distinctive {@link Feature} instances from a {@link Sample}.
 * The component is {@link Serializable} because is part of a {@link Classifier} which can be saved.
 *
 * @param <Feature> a distinctive characteristic present in an observation within a population
 * @param <Sample>   an observation within a population containing a collection of distinctive features
 */
public interface Extractor<Feature, Sample> extends Serializable {

    /**
     * Processes a {@code sample} and returns the resulting collection of {@link Feature} instances.
     * The {@code sample} must be non null but can be empty.
     *
     * @param sample the {@link Sample} to process
     *
     * @return the resulting collection of {@link Feature} instances
     */
    Collection<Feature> process(Sample sample);
}
