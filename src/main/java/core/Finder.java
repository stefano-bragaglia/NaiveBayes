package core;

import java.util.Collection;

/**
 * TODO Add some meaningful class description...
 */
public interface Finder<Sample, Feature> {
	/**
	 * @param sample
	 * @return
	 */
	Collection<Feature> process(Sample sample);
}
