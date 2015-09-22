package core;

/**
 * TODO Add some meaningful class description...
 */
public interface Refiner<Sample> {
	/**
	 * @param sample
	 * @return
	 */
	Sample process(Sample sample);
}
