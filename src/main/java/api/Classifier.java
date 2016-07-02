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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic <i>Naive Bayes classifier</i>.
 *
 * @param <Feature>  an observed characteristic to be used for classification
 * @param <Category> a group in which the {@link Feature}s are classified
 */
public class Classifier<Feature, Category> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Classifier.class);

    private static final long serialVersionUID = 1917935569645011813L;

    /**
     * The occurrences by {@link Feature} per {@link Category} read by this {@link Classifier}.
     */
    private final Map<Category, Map<Feature, Double>> features;

    /**
     * The total occurrences by {@link Feature} read by this {@link Classifier}.
     */
    private final Map<Feature, Double> totals;

    /**
     * Default constructor for a {@see Classifier} initialised to the content of the given {@see Trainer}.
     *
     * @param builder the {@see Trainer} whose features data is used to initialise this {@see Classifier}
     */
    private Classifier(Builder<Feature, Category> builder) {
        Objects.requireNonNull(builder);

        features = new HashMap<>(builder.features);
        totals = new HashMap<>(builder.totals);
    }

    /**
     * Loads a {@link Classifier} from the given {@code path}.
     *
     * @param path       the {@link Path} of the {@link Classifier} to load
     * @param <Feature>  an observed characteristic to be used for classification
     * @param <Category> a group in which the {@link Feature}s are classified
     *
     * @return the {@link Classifier} at the given {@code path}
     */
    @SuppressWarnings(value = "uncheked")
    public static <Feature, Category> Classifier<Feature, Category> load(Path path) {
        Objects.requireNonNull(path);

        Classifier<Feature, Category> result = null;
        try {
            InputStream stream = Files.newInputStream(path);
            ObjectInputStream output = new ObjectInputStream(stream);
            result = (Classifier<Feature, Category>) output.readObject();
            output.close();
            stream.close();
        } catch (ClassNotFoundException | IOException e) {
            Classifier.logger.warn(e.toString());
        }
        return result;
    }

    /**
     * Returns the most likely {@link Category} for the given map of frequencies by {@link Feature}, if any.
     *
     * @param features the map of frequencies by {@link Feature} to classify
     *
     * @return the most likely {@link Category} for the given map of frequencies by {@link Feature}
     */
    public Optional<Category> classify(Map<Feature, Double> features) {
        Objects.requireNonNull(features);

        Map<Category, Double> argmax = aggregate(features);
        List<Entry<Category, Double>> entries = sort(argmax);
        return entries.size() > 0 ? Optional.of(entries.get(0).getKey()) : Optional.empty();
    }

    /**
     * Sorts the given distribution of probability by {@link Category} by decreasing probability and returns the
     * resulting list of entries.
     *
     * @param distribution the distribution of probability by {@link Category} to be sorted
     * @param <Category>   a group used for classification
     *
     * @return the list of {@link Category} in decreasing order of probability
     */
    protected static <Category> List<Entry<Category, Double>> sort(Map<Category, Double> distribution) {
        if (distribution.size() > 0) {
            List<Entry<Category, Double>> entries = new ArrayList<>(distribution.entrySet());
            Collections.sort(entries, (o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));
            return entries;
        }
        return Collections.emptyList();
    }

    /**
     * Aggregates the given map of frequencies by {@link Feature} into a distribution of probabilities
     * by {@link Category}.
     *
     * @param features the map of frequencies by {@link Feature} to aggregate
     *
     * @return the distribution of probabilities by {@link Category}
     */
    private Map<Category, Double> aggregate(Map<Feature, Double> features) {
        Map<Category, Double> result = new HashMap<>();
        for (Feature feature : features.keySet()) {
            Objects.requireNonNull(feature);
            Double value = features.get(feature);
            Objects.requireNonNull(value);
            for (Category category : this.features.keySet()) {
                double probability = value * Math.log(probability(feature, category));
                result.put(category, result.getOrDefault(category, 0.0) + probability);
            }
        }
        return result;
    }

    /**
     * Returns the probability of the given {@code feature} to appear in the given {@code category}.
     * Includes the additive smoothing to avoid math errors in the <i>Maximum-a-Posteriori (MAP) formula</i>.
     *
     * @param feature  the {@link Feature} whose probability to appear in the given {@code category} is sought
     * @param category the {@link Category} in which the given {@code feature} should appear
     *
     * @return the probability that the given {@code feature} appears in the given {@code category}
     */
    public double probability(Feature feature, Category category) {
        Objects.requireNonNull(feature);
        Objects.requireNonNull(category);
        if (!features.containsKey(category)) {
            throw new IllegalArgumentException("'category' is not known");
        }

        double numerator = 1.0 + features.get(category).getOrDefault(feature, 0.0);
        double denominator = totals.getOrDefault(feature, 0.0) + features.size();
        return numerator / denominator;
    }

    /**
     * Returns the most likely {@link Category} for the given map of frequencies by {@link Feature}, if any.
     * The probability of the most likely {@link Category} must be higher than any other {@link Category} by the
     * given {@code percent} value
     *
     * @param features the map of frequencies by {@link Feature} to classify
     * @param percent  the desired gap between the probability of the most likely {@link Category} and the others
     *
     * @return the most likely {@link Category} for the given map of frequencies by {@link Feature}
     */
    public Optional<Category> classify(Map<Feature, Double> features, double percent) {
        Objects.requireNonNull(features);
        if (percent < 0.0 || percent > 1.0) {
            throw new IllegalArgumentException("'percent' is not in the range [0.0, 1.0]");
        }

        Map<Category, Double> argmax = aggregate(features);
        List<Entry<Category, Double>> entries = sort(argmax);
        if (!entries.isEmpty()) {
            Entry<Category, Double> entry = entries.get(0);
            if (entries.size() > 1 && (1.0 + percent) * entries.get(1).getValue() >= entry.getValue()) {
                return Optional.empty();
            }
            return Optional.of(entry.getKey());
        }
        return Optional.empty();
    }

    /**
     * Saves this {@link Classifier} into the given {@code path}.
     *
     * @param path the {@link Path} where to save this {@link Classifier}
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

    /**
     * Evaluates the given map of frequencies by {@link Feature} and returns the distribution of probability for each
     * {@link Category}, if any.
     *
     * @param features the map of frequencies by {@link Feature} to evaluate
     *
     * @return the distribution of probability for each {@link Category}, if any
     */
    protected Optional<Entry<Category, Double>> evaluate(Map<Feature, Double> features) {
        Objects.requireNonNull(features);

        Map<Category, Double> argmax = distribution(features);
        List<Entry<Category, Double>> entries = sort(argmax);
        return entries.size() > 0 ? Optional.of(entries.get(0)) : Optional.empty();
    }

    /**
     * Returns the map of frequencies by {@link Feature} for the given map of occurrences by {@link Feature}.
     *
     * @param features the map of occurrences by {@link Feature} to convert
     *
     * @return the equivalent map of frequencies by {@link Feature}
     */
    protected Map<Category, Double> distribution(Map<Feature, Double> features) {
        Objects.requireNonNull(features);

        Map<Category, Double> result = aggregate(features);
        double sum = result.values().stream().mapToDouble(x -> x).sum();
        for (Category category : result.keySet()) {
            result.put(category, result.getOrDefault(category, 0.0) / sum);
        }
        return result;
    }

    /**
     * An helper class to initialise a <i>Naive Bayes classifier</i>.
     *
     * @param <Feature>  an observed characteristic to be used for classification
     * @param <Category> a group in which the {@link Feature}s are classified
     */
    public static class Builder<Feature, Category> {

        /**
         * The occurrences by {@link Feature} per {@link Category} read by this {@link Classifier}.
         */
        private final Map<Category, Map<Feature, Double>> features;

        /**
         * The total occurrences by {@link Feature} read by this {@link Classifier}.
         */
        private final Map<Feature, Double> totals;

        /**
         * Default constructor for an empty {@see Trainer}.
         */
        public Builder() {
            features = new HashMap<>();
            totals = new HashMap<>();
        }

        private Builder(Map<Category, Map<Feature, Double>> features, Map<Feature, Double> totals) {
            Objects.requireNonNull(features);
            Objects.requireNonNull(totals);

            this.features = new HashMap<>(features);
            this.totals = new HashMap<>(totals);
        }

        /**
         * Default constructor for a {@see Trainer} initialised to the content of the given {@see Classifier}.
         *
         * @param classifier the {@see Classifier} whose features data is used to initialise this {@see Trainer}
         */
        public Builder(Classifier<Feature, Category> classifier) {
            Objects.requireNonNull(classifier);

            features = new HashMap<>(classifier.features);
            totals = new HashMap<>(classifier.totals);
        }

        /**
         * Builds a {@link Classifier} using the data accumulated so far.
         *
         * @param tf_idf applies TF_IDF if {@code true}
         *
         * @return a {@link Classifier} initialised with the data accumulated so far
         */
        public Classifier<Feature, Category> build(boolean tf_idf) {
            if (tf_idf) {
                double n = features.size(); // number of categories;
                Map<Feature, Double> frequency = new HashMap<>(); // count of appearances
                for (Map<Feature, Double> values : features.values()) {
                    for (Feature feature : values.keySet()) {
                        frequency.put(feature, 1.0 + frequency.getOrDefault(feature, 0.0));
                    }
                }

                Map<Feature, Double> newTotals = new HashMap<>();
                Map<Category, Map<Feature, Double>> newFeatures = new HashMap<>();
                for (Category category : features.keySet()) {
                    Map<Feature, Double> values = features.get(category);
                    for (Feature feature : values.keySet()) {
                        double value = frequency.getOrDefault(feature, n);
                        if (value < n) {

                            Map<Feature, Double> newValues = newFeatures.get(category);
                            if (null == newValues) {
                                newValues = new HashMap<>();
                                newFeatures.put(category, newValues);
                            }
                            double newValue = Math.log(n / frequency.get(feature)) * values.get(feature);
                            newValues.put(feature, newValue);
                            newTotals.put(feature, newValue + newTotals.getOrDefault(feature, 0.0));
                        }
                    }
                }
                return new Classifier<>(new Builder<>(newFeatures, newTotals));
            }
            return new Classifier<>(this);
        }

        /**
         * Builds a {@link Classifier} using the data accumulated so far.
         *
         * @return a {@link Classifier} initialised with the data accumulated so far
         */
        public Classifier<Feature, Category> build() {
            return new Classifier<>(this);
        }

        /**
         * Adds the given set of features and their number of occurrences to the features set under the given category.
         * The category is created if not present. No null objects and values are allowed among features.
         *
         * @param category the {@link Category} for the given map of occurrences by {@link Feature}
         * @param features the map of occurrences by {@link Feature} to add to the given {@code category}
         *
         * @return this {@link Builder}
         */
        public Builder<Feature, Category> add(Category category, Map<Feature, Double> features) {
            Objects.requireNonNull(category);
            Objects.requireNonNull(features);

            Map<Feature, Double> current = this.features.get(category);
            if (null == current) {
                current = new HashMap<>();
                this.features.put(category, current);
            }
            for (Feature feature : features.keySet()) {
                Objects.requireNonNull(feature);
                Double value = features.get(feature);
                Objects.requireNonNull(value);
                current.put(feature, value + current.getOrDefault(feature, 0.0));
                totals.put(feature, value + totals.getOrDefault(feature, 0.0));
            }
            return this;
        }

        /**
         * Removes the {@link Feature}s associated with the given {@code category} (if any).
         *
         * @param category the {@link Category} whose {@link Feature}s have to be removed
         *
         * @return this {@link Builder}
         */
        public Builder remove(Category category) {
            Objects.requireNonNull(category);

            Map<Feature, Double> current = features.get(category);
            if (null != current) {
                for (Feature feature : current.keySet()) {
                    Double value = totals.get(feature) - current.get(feature);
                    if (value < 0) {
                        throw new IllegalStateException("the features data is corrupted");
                    }
                    totals.put(feature, value);
                }
            }
            features.remove(category);
            return this;
        }

    }

}
