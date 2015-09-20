package core;

import java.util.Collection;

/**
 * TODO Add some meaningful class description...
 */
public interface Processor<Sample, Feature> {
	/**
	 * @param sample
	 * @return
	 */
	Collection<Feature> analyse(Sample sample);
}
