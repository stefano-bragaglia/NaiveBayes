package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Add some meaningful class description...
 */
public class CompoundClassifier<Sample, Feature, Category> implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(CompoundClassifier.class);

	private static final long serialVersionUID = 2978152269469558340L;

	private final Map<Classifier<Feature, Category>, Processor<Sample, Feature>> classifiers;

	private CompoundClassifier(Builder<Sample, Feature, Category> builder) {
		this.classifiers = new HashMap<>(builder.classifiers);
	}

	/**
	 * Loads the content of the file at the given {@see Path} into a {@see Classifier}.
	 *
	 * @param path the {@see Path} to the file whose content has to be loaded in a {@see Classifier}
	 * @return the {@see Classifier} initialised to the content of the file at the given {@see Path}
	 */
	@SuppressWarnings(value = "uncheked")
	public static <Sample, Feature, Category> CompoundClassifier<Sample, Feature, Category> load(Path path) {
		Objects.requireNonNull(path);

		CompoundClassifier<Sample, Feature, Category> result = null;
		try {
			InputStream stream = Files.newInputStream(path);
			ObjectInputStream output = new ObjectInputStream(stream);
			result = (CompoundClassifier<Sample, Feature, Category>) output.readObject();
			output.close();
			stream.close();
		} catch (ClassNotFoundException | IOException e) {
			logger.warn(e.toString());
		}
		return result;
	}

	public Optional<Category> classify(Sample sample) {
		Objects.requireNonNull(sample);

		Map<Category, Double> result = new HashMap<>();
		Map<Processor<Sample, Feature>, Map<Feature, Double>> cache = new HashMap<>();
		for (Classifier<Feature, Category> classifier : classifiers.keySet()) {
			Processor<Sample, Feature> processor = classifiers.get(classifier);
			Map<Feature, Double> features = cache.get(processor);
			if (null == features) {
				features = processor.process(sample);
				cache.put(processor, features);
			}
			Map<Category, Double> distribution = classifier.distribution(features);
			for (Category type : distribution.keySet()) {
				Double v1 = distribution.get(type);
				Double v2 = result.getOrDefault(type, 0.0);
				result.put(type, v1 + v2 - v1 * v2);
				v2 = result.getOrDefault(type, 0.0);
				result.put(type, v1 + v2 - v1 * v2);
			}
		}
		List<Map.Entry<Category, Double>> entries = Classifier.sort(result);
		return entries.size() > 0 ? Optional.of(entries.get(0).getKey()) : Optional.empty();
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

	public static class Builder<Sample, Feature, Category> {

		private final Map<Classifier<Feature, Category>, Processor<Sample, Feature>> classifiers;

		public Builder() {
			this.classifiers = new HashMap<>();
		}

		public Builder<Sample, Feature, Category> add(
				Classifier<Feature, Category> classifier,
				Processor<Sample, Feature> processor) {
			Objects.requireNonNull(classifier);
			Objects.requireNonNull(processor);

			this.classifiers.put(classifier, processor);
			return this;
		}

		public Builder<Sample, Feature, Category> remove(Classifier<Feature, Category> classifier) {
			Objects.requireNonNull(classifier);

			this.classifiers.remove(classifier);
			return this;
		}

		public CompoundClassifier<Sample, Feature, Category> build() {
			return new CompoundClassifier<>(this);
		}

	}

}
