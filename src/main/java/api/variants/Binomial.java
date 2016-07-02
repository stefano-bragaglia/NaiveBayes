package api.variants;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import api.Variant;

/**
 * This class implements a <i><b>Binomial</b> Naive Bayes classifier</i>.
 * This classifier only considers the presence of a {@link Feature} for classification.
 * (All the {@link Feature}s have weight 1.)
 *
 * @param <Feature> a characteristic used for classification
 */
public class Binomial<Feature> implements Variant<Feature> {

    private static final long serialVersionUID = -5362979526653488866L;

    @Override
    public Map<Feature, Double> digest(Collection<Feature> features) {
        Objects.requireNonNull(features);

        Map<Feature, Double> result = new HashMap<>();
        features = new HashSet<>(features);
        for (Feature feature : features) {
            Objects.requireNonNull(feature);
            result.put(feature, 1.0);
        }
        return result;
    }

}
