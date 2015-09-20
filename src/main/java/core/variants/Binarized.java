package core.variants;

import java.util.*;

import core.Variant;

/**
 * TODO Add some meaningful class description...
 */
public class Binarized<Feature> implements Variant<Feature> {

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
