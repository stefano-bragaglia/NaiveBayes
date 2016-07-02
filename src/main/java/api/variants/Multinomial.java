package api.variants;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import api.Variant;

/**
 * This class implements a <i><b>Multinomial</b> Naive Bayes classifier</i>.
 * This classifier only considers the number of occurrences of each {@link Feature} for classification.
 * (The weight of each {@link Feature} is its number of occurrences.)
 *
 * @param <Feature> a characteristic used for classification
 */
public class Multinomial<Feature> implements Variant<Feature> {

    private static final long serialVersionUID = 6297472457709950780L;

    @Override
    public Map<Feature, Double> digest(Collection<Feature> features) {
        Objects.requireNonNull(features);

        Map<Feature, Double> result = new HashMap<>();
        for (Feature feature : features) {
            Objects.requireNonNull(feature);
            result.put(feature, 1.0 + result.getOrDefault(feature, 0.0));
        }
        return result;
    }

}
