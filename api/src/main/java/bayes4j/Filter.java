package bayes4j;

import java.io.Serializable;
import java.util.Collection;

/**
 * A component that appropriately filters a collection of {@link Feature} instances according to some criteria.
 * The component is {@link Serializable} because is part of a {@link Classifier} which can be saved.
 *
 * @param <Feature> a distinctive characteristic present in an observation within a population
 */
public interface Filter<Feature> extends Serializable {
    /**
     * Processes the given collection of {@code features} and returns the filtered collection of {@link Feature}
     * instances according to some criteria.
     *
     * @param features the collection of {@link Feature} instances to filter
     *
     * @return the filtered collection of {@link Feature} instances
     */
    Collection<Feature> process(Collection<Feature> features);
}
