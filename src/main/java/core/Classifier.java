package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Multinomial Naive Bayes classifier.
 *
 * @param <Feature>  one of the observed features to subsume the category of the entities to be classified
 * @param <Category> one of the possible categories for the entities to be classified
 */
public class Classifier<Feature, Category> implements Serializable {

	private static final long serialVersionUID = 1917935569645011813L;

	private static final Logger logger = LoggerFactory.getLogger(Classifier.class);

	/**
	 * The features and their number of occurrences by category of this {@see Classifier}.
	 */
	private final Map<Category, Map<Feature, Double>> features;

	/**
	 * The totals by feature of this {@see Classifier}.
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
	 * Loads the content of the file at the given {@see Path} into a {@see Classifier}.
	 *
	 * @param path the {@see Path} to the file whose content has to be loaded in a {@see Classifier}
	 * @return the {@see Classifier} initialised to the content of the file at the given {@see Path}
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
			logger.warn(e.toString());
		}
		return result;
	}

	/**
	 * Returns the most likely {@see Category} for the entity whose set of features and their number of occurrences is
	 * passed as parameter.
	 *
	 * @param features the set of features and their number of occurrences of the entity to be classified
	 * @return the most likely {@see Category} of the entity to be classified
	 */
	public Category classify(Map<Feature, Double> features) {
		Objects.requireNonNull(features);

		Map<Category, Double> argmax = distribution(features);
		if (0 == argmax.size()) {
			return null;
		}
		ArrayList<Entry<Category, Double>> entries = new ArrayList<>(argmax.entrySet());
		Collections.sort(entries, (o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));
		return entries.get(0).getKey();
	}

	public Category classify(Map<Feature, Double> features, double percent) {
		Objects.requireNonNull(features);
		if (percent < 0.0 || percent > 1.0) {
			throw new IllegalArgumentException("'percent' is not in the range [0.0, 1.0]");
		}

		Map<Category, Double> argmax = distribution(features);
		if (0 == argmax.size()) {
			return null;
		}
		ArrayList<Entry<Category, Double>> entries = new ArrayList<>(argmax.entrySet());
		Collections.sort(entries, (o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));
		Entry<Category, Double> entry = entries.get(0);
		if (entries.size() > 1 && (1.0 + percent) * entries.get(1).getValue() >= entry.getValue()) {
			return null;
		}
		return entry.getKey();
	}

	private Map<Category, Double> distribution(Map<Feature, Double> features) {
		Objects.requireNonNull(features);

		double total = 0.0;
		Map<Category, Double> result = new HashMap<>();
		for (Feature feature : features.keySet()) {
			Objects.requireNonNull(feature);
			Double value = features.get(feature);
			Objects.requireNonNull(value);
			for (Category category : this.features.keySet()) {
				double probability = value * Math.log(compute(feature, category));
				total += probability;
				result.put(category, result.getOrDefault(category, 0.0) + probability);
			}
		}
//		for (Category category : result.keySet()) {
//			result.put(category, result.get(category) / total);
//		} // The distribution sums up to 1
		return result;
	}

	/**
	 * Computes and returns the probability of the given {@see Feature} to occur in an entity pertaining to the given
	 * {@see Category}.
	 * Includes the additive smoothing to avoid math errors in the Maximum-a-Posteriori (MAP) formula.
	 *
	 * @param feature  the {@see Feature} whose probability given the {@see Category} is desired
	 * @param category the {@see Category} conditioning the {@see Feature} whose probability is desired
	 * @return the probability of the given {@see Feature} to occur in an entity pertaining to the given {@see
	 * Category}
	 */
	public double compute(Feature feature, Category category) {
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
	 * Saves this {@see Classifier} into the file at the given {@see Path}.
	 *
	 * @param path the {@see Path} to the file where to save this {@see Classifier}
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
	 * An helper class to train and build a Multinomial Naive Bayes classifier.
	 *
	 * @param <Feature>  one of the observed features to subsume the category of the entities to be classified
	 * @param <Category> one of the possible categories for the entities to be classified
	 */
	public static class Builder<Feature, Category> {

		/**
		 * The features and their number of occurrences by category of this {@see Trainer}.
		 */
		private final Map<Category, Map<Feature, Double>> features;

		/**
		 * The totals by feature of this {@see Classifier}.
		 */
		private final Map<Feature, Double> totals;

		/**
		 * Default constructor for an empty {@see Trainer}.
		 */
		public Builder() {
			features = new HashMap<>();
			totals = new HashMap<>();
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
		 * Adds the given set of features and their number of occurrences to the features set under the given category.
		 * The category is created if not present. No null objects and values are allowed among features.
		 *
		 * @param category the category to whom the set of features and their number of occurrences fall within
		 * @param features the set of features and their number of occurrences
		 * @return this builder
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
		 * Builds a {@see Classifier} with the features data accumulated in this {@see Trainer}.
		 *
		 * @return the {@see Classifier} initialised with the features data in this {@see Trainer}
		 */
		public Classifier<Feature, Category> build() {
			return new Classifier<>(this);
		}

		/**
		 * Removes the features data of this category (if any).
		 *
		 * @param category the category whose features data has to be removed
		 * @return this builder
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
