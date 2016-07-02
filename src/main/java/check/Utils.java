package check;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * TODO Add some meaningful class description...
 */
public class Utils<Feature> {

	/**
	 * @param o1
	 * @param o2
	 * @return
	 */
	public double similar(Map<Feature, Double> o1, Map<Feature, Double> o2) {
		Objects.requireNonNull(o1);
		Objects.requireNonNull(o2);

		double scalar = 0.0;
		double normO1 = 0.0;
		double normO2 = 0.0;
		Set<Feature> features = new HashSet<>(o1.keySet());
		features.retainAll(o2.keySet());
		for (Feature feature : features) {
			double o1Value = o1.get(feature);
			double o2Value = o2.get(feature);
			scalar += o1Value * o2Value;
			normO1 += o1Value * o1Value;
			normO2 += o2Value * o2Value;
		}
		return (scalar * scalar) / (normO1 * normO2);
	}

}
