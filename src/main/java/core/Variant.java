package core;

import java.util.Collection;
import java.util.Map;

/**
 * TODO Add some meaningful class description...
 */
public interface Variant<Feature> {

	/**
	 * @param features
	 * @return
	 */
	Map<Feature, Double> digest(Collection<Feature> features);

}
