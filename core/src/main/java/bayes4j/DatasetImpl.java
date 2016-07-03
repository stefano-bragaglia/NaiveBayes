package bayes4j;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

/**
 * This class implements a {@link Dataset}.
 *
 * @param <Sample>   an observation within a population containing a collection of distinctive features
 * @param <Category> a class that collects a population of observations that share a common property
 */
public final class DatasetImpl<Sample, Category> implements Dataset<Sample, Category> {

    private static final long serialVersionUID = -2209582602204320299L;

    private final Map<Category, Set<Sample>> samplesByCategory;

    DatasetImpl() {
        this.samplesByCategory = new HashMap<>();
    }

    @Override
    public void append(Category category, Sample sample) {
        Set<Sample> sampleSet = samplesByCategory.get(requireNonNull(category));
        if (null == sampleSet) {
            sampleSet = new HashSet<>();
            samplesByCategory.put(category, sampleSet);
        }
        sampleSet.add(requireNonNull(sample));
    }

    @Override
    public void append(Category category, Collection<Sample> samples) {
        Set<Sample> sampleSet = samplesByCategory.get(requireNonNull(category));
        if (null == sampleSet) {
            sampleSet = new HashSet<>();
            samplesByCategory.put(category, sampleSet);
        }
        sampleSet.addAll(requireNonNull(samples));
    }

    @Override
    public Collection<Category> getCategories() {
        return samplesByCategory.keySet();
    }

    @Override
    public Collection<Sample> getSamples(Category category) {
        return samplesByCategory.getOrDefault(requireNonNull(category), emptySet());
    }

    @Override
    public int size() {
        return samplesByCategory.values().stream().mapToInt(Set::size).sum();
    }

    @Override
    public void save(Path path) {
        requireNonNull(path);

        try {
            OutputStream stream = Files.newOutputStream(path);
            ObjectOutputStream output = new ObjectOutputStream(stream);
            output.writeObject(this);
            output.close();
            stream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("'path' is not valid: " + path, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatasetImpl<?, ?> dataset = (DatasetImpl<?, ?>) o;
        return samplesByCategory.equals(dataset.samplesByCategory);
    }

    @Override
    public int hashCode() {
        return samplesByCategory.hashCode();
    }

    @Override
    public String toString() {
        return "DatasetImpl{" +
               "samplesByCategory=" + samplesByCategory +
               '}';
    }
}
