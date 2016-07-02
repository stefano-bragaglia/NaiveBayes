package core;

import java.io.Serializable;
import java.util.Collection;

/**
 * TODO Add some meaningful class description...
 */
public interface Finder<Sample, Feature> extends Serializable {
	/**
	 * @param sample
	 * @return
	 */
	Collection<Feature> process(Sample sample);
}
