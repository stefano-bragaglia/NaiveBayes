package core;

import java.io.Serializable;

/**
 * TODO Add some meaningful class description...
 */
public interface Refiner<Sample> extends Serializable {
	/**
	 * @param sample
	 * @return
	 */
	Sample process(Sample sample);
}
