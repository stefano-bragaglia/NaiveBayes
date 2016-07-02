package core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * TODO Add some meaningful class description...
 */
public interface Variant<Feature> extends Serializable {

	/**
	 * @param features
	 * @return
	 */
	Map<Feature, Double> digest(Collection<Feature> features);

}
