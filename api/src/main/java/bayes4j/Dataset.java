package bayes4j;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Collection;

/**
 * A set of {@link Sample}s either used for learning, tuning or assessing the performances of a classifier.
 * The {@link Sample}s are organised by {@link Category} to express the fact that the features that they contain
 * share a common trait.
 *
 * @param <Sample>   an observation within a population containing a collection of distinctive features
 * @param <Category> a class that collects a population of observations that share a common property
 */
public interface Dataset<Sample, Category> extends Serializable {

    /**
     * Appends the given {@code sample} to the collection of {@link Sample} instances associated to the specified
     * {@link Category}. If such collection does not exists, it is automatically created.
     *
     * @param category the {@link Category} to which to append the {@code sample}
     * @param sample   the {@link Sample} to append
     */
    void append(Category category, Sample sample);

    /**
     * Appends the given collections of {@code samples} to the collection of {@link Sample} instances associated to
     * the specified {@link Category}. If such collection does not exists, it is automatically created.
     *
     * @param category the {@link Category} to which to append the {@code samples}
     * @param samples  the {@link Sample} instances to append
     */
    void append(Category category, Collection<Sample> samples);

    /**
     * Returns the collections of {@link Category} instances currently included in this dataset.
     *
     * @return the collections of {@link Category} instances currently included in this dataset
     */
    Collection<Category> getCategories();

    /**
     * Returns the collections of {@link Sample} instances associated to the given {@code category} currently
     * included in this dataset. If the given {@code category} is not found in this dataset, an empty collection
     * is returned.
     *
     * @param category the {@link Category} whose associated {@link Sample} instances are being returned
     *
     * @return the collections of {@link Sample} instances associated to the given {@code category} currently
     * included in this dataset
     */
    Collection<Sample> getSamples(Category category);

    /**
     * Returns the number of {@link Sample} instances in this dataset.
     *
     * @return the number of {@link Sample} instances in this dataset
     */
    int size();

    /**
     * Saves this dataset to a file at the given {@code path}
     *
     * @param path the {@link Path} where to save this dataset
     */
    void save(Path path);
}
