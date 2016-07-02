package core.variants;

import java.util.*;

import core.Variant;

/**
 * TODO Add some meaningful class description...
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
