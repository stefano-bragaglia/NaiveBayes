package api;

import java.io.Serializable;

/**
 * Implement this class to create a {@link Refiner}, a component that filters, cleans ot otherwise curate the content
 * of a {@link Sample} before it is used in classification tasks.
 *
 * @param <Sample> an entity to learn
 */
public interface Refiner<Sample> extends Serializable {
    /**
     * Processes the content of the given {@code sample} and returns the refined result of this operation.
     *
     * @param sample the {@link Sample} to return
     *
     * @return the refined {@link Sample}
     */
    Sample process(Sample sample);
}
