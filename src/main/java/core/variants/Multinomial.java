package core.variants;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import core.Variant;

/**
 * TODO Add some meaningful class description...
 */
public class Multinomial<Feature> implements Variant<Feature> {

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
