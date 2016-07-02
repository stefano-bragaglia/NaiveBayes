package api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Implement this class to create a generic {@link Variant} of a <i>Naive Bayes classifier</i>.
 *
 * @param <Feature> a characteristic used for classification
 */
public interface Variant<Feature> extends Serializable {

    /**
     * Digests a collection of {@code features} and returns a map of the frequencies by unique {@code features}.
     *
     * @param features the collection of {@code features} to digest
     *
     * @return a map of the frequencies by unique {@code features}
     */
    Map<Feature, Double> digest(Collection<Feature> features);

}
