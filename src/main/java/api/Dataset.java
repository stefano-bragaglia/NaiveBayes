package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a {@link Dataset}, or a map of sets of {@link Sample}s by {@link Category}.
 * A {@link Dataset} is used for training a <i>Naive Bayes classifier</i>: the {@link Sample}s in each set are
 * examples from whom the classifier is expected to learn how to recognise a {@link Category}.
 * {@link Dataset}s can be saved to file and reloaded at convenience for later use.
 *
 * @param <Sample>   an example from whom a classifier will learn how to recognise a {@link Category}
 * @param <Category> a class of interest to be used for classification
 */
public class Dataset<Sample, Category> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Dataset.class);

    private static final long serialVersionUID = -2209582602204320299L;

    /**
     * The set of {@link Sample}s by {@link Category} of this {@link Dataset}.
     */
    private Map<Category, Set<Sample>> map;

    /**
     * Creates an empty {@link Dataset}.
     */
    public Dataset() {
        this.map = new HashMap<>();
    }

    /**
     * Loads a {@link Dataset} from the given {@code path}.
     *
     * @param path       the {@link Path} of the {@link Dataset} to load
     * @param <Sample>   an example from whom a classifier will learn how to recognise a {@link Category}
     * @param <Category> a class of interest to be used for classification
     *
     * @return the {@link Dataset} at the given {@code path}
     */
    @SuppressWarnings(value = "unchecked")
    public static <Sample, Category> Dataset<Sample, Category> load(Path path) {
        Objects.requireNonNull(path);

        Dataset<Sample, Category> result = null;
        try {
            InputStream stream = Files.newInputStream(path);
            ObjectInputStream output = new ObjectInputStream(stream);
            result = (Dataset<Sample, Category>) output.readObject();
            for (Category category : result.map.keySet()) {
                List<Sample> samples = new ArrayList<>(result.map.get(category));
                Collections.shuffle(samples);
                result.map.put(category, new HashSet<>(samples));
            }
            output.close();
            stream.close();
        } catch (ClassNotFoundException | IOException e) {
            logger.warn(e.toString());
        }
        return result;
    }

    /**
     * Adds the given {@code sample} as an example for the given {@code category}.
     *
     * @param category the {@link Category} to which the given {@code sample} pertains
     * @param sample   a {@link Sample} for the given {@code category}
     */
    public void add(Category category, Sample sample) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(sample);

        Set<Sample> set = map.get(category);
        if (null == set) {
            set = new HashSet<>();
            map.put(category, set);
        }
        set.add(sample);
    }

    /**
     * Adds the given {@link Collection} of {@code samples} as examples for the given {@code category}.
     *
     * @param category the {@link Category} to which the given {@code samples} pertain
     * @param samples  a {@link Collection} of {@link Sample} for the given {@code category}
     */
    public void add(Category category, Collection<Sample> samples) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(samples);

        Set<Sample> set = map.get(category);
        if (null == set) {
            set = new HashSet<>();
            map.put(category, set);
        }
        for (Sample sample : samples) {
            Objects.requireNonNull(sample);
            set.add(sample);
        }
    }

    /**
     * Removes the given {@code category} for this {@link Dataset} (if present).
     *
     * @param category the {@link Category} to be removed
     */
    public void remove(Category category) {
        Objects.requireNonNull(category);

        map.remove(category);
    }

    /**
     * Returns the number of {@link Sample} instances in this {@link Dataset}.
     *
     * @return the number of {@link Sample} instances in this {@link Dataset}
     */
    public int size() {
        return map.values().stream()
                  .mapToInt(Set::size)
                  .sum();
    }

    /**
     * Returns the number of {@link Sample} instances for the given {@code category} in this {@link Dataset}.
     *
     * @param category the {@link Category} whose number of {@link Sample} instances is sought
     *
     * @return the number of {@link Sample} instances for the given {@code category} in this {@link Dataset}
     */
    public int size(Category category) {
        Objects.requireNonNull(category);

        if (!map.containsKey(category)) {
            return 0;
        }
        assert map.size() == 2 : "2 documents";
        return map.get(category).size();
    }

    /**
     * Checks whether the given {@code sample} is present in this {@link Dataset}.
     *
     * @param sample the {@link Sample} to check
     *
     * @return {@code true} if the given {@code sample} is present in this {@link Dataset}, {@code false} otherwise
     */
    public boolean contains(Sample sample) {
        Objects.requireNonNull(sample);

        for (Set<Sample> samples : map.values()) {
            if (samples.contains(sample)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the {@link Collection} of {@link Category} instances in this {@link Dataset}.
     *
     * @return the {@link Collection} of {@link Category} instances in this {@link Dataset}
     */
    public Collection<Category> categories() {
        return map.keySet();
    }

    /**
     * Returns the {@link Collection} of {@link Sample} instances for the given {@code category} in this
     * {@link Dataset}.
     *
     * @param category the {@link Category} to select the samples to return
     *
     * @return the {@link Collection} of {@link Sample} instances for the given {@code category} in this
     * {@link Dataset}
     */
    public Collection<Sample> getSamples(Category category) {
        Objects.requireNonNull(category);

        if (!map.containsKey(category)) {
            return Collections.emptySet();
        }
        return map.get(category);
    }

    /**
     * Saves this {@link Dataset} into the given {@code path}.
     *
     * @param path the {@link Path} where to save this {@link Dataset}
     */
    public void save(Path path) {
        Objects.requireNonNull(path);

        try {
            OutputStream stream = Files.newOutputStream(path);
            ObjectOutputStream output = new ObjectOutputStream(stream);
            output.writeObject(this);
            output.close();
            stream.close();
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

}
