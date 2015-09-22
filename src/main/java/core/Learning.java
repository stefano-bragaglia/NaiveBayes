package core;

import java.util.*;

/**
 * TODO Add some meaningful class description...
 */
public class Learning {

	/**
	 * @param training
	 * @param processor
	 * @param <Sample>
	 * @param <Feature>
	 * @param <Category>
	 * @return
	 */
	public static <Sample, Feature, Category> Classifier<Feature, Category> train(
			Dataset<Sample, Category> training,
			Processor<Sample, Feature> processor) {
		Objects.requireNonNull(training);
		Objects.requireNonNull(processor);

		Classifier.Builder<Feature, Category> builder = new Classifier.Builder<>();
		for (Category category : training.categories()) {
			Collection<Sample> samples = training.getSamples(category);
			for (Sample sample : samples) {
				Map<Feature, Double> features = processor.process(sample);
				builder.add(category, features);
			}
		}
		return builder.build();
	}

	/**
	 * @param training
	 * @param samples
	 * @param iterations
	 * @param processors
	 * @param <Sample>
	 * @param <Feature>
	 * @param <Category>
	 * @return
	 */
	public static <Sample, Feature, Category> Collection<Classifier<Feature, Category>> cotrain(
			Dataset<Sample, Category> training,
			Collection<Sample> samples,
			int iterations,
			Processor<Sample, Feature>... processors) {
		Objects.requireNonNull(training);
		Objects.requireNonNull(samples);
		if (iterations < 1) {
			throw new IllegalArgumentException("'iterations' must be greater or equals to 1: " + iterations);
		}
		Objects.requireNonNull(processors);

		Map<Processor<Sample, Feature>, Classifier<Feature, Category>> classifiers = new HashMap<>();
		for (int k = 0; k < iterations; k++) {
			for (Processor<Sample, Feature> processor : processors) {
				Objects.requireNonNull(processor);
				Classifier<Feature, Category> classifier = Learning.train(training, processor);
				classifiers.put(processor, classifier);
			}
			Map<Category, List<Processor<Sample, Feature>>> needed = new HashMap<>();
			for (Category category : training.categories()) {
				needed.put(category, Arrays.asList(processors));
			}
			Iterator<Sample> iterator = samples.iterator();
			while (!needed.isEmpty() && iterator.hasNext()) {
				Sample sample = iterator.next();
				for (int i = 0; i < processors.length; i++) {
					Map<Feature, Double> features = processors[i].process(sample);
					Classifier<Feature, Category> classifier = classifiers.get(processors[i]);
					Category category = classifier.classify(features);
					List<Processor<Sample, Feature>> available = needed.get(category);
					if (available.contains(processors[i])) {
						training.add(category, sample);
						available.remove(processors[i]);
						break;
					}
				}
			}
		}
		return classifiers.values();
	}

	/**
	 * @param classifier
	 * @param dataset
	 * @param processor
	 * @param variant
	 * @param <Sample>
	 * @param <Feature>
	 * @param <Category>
	 * @return
	 */
	public static <Sample, Feature, Category> Map<Category, Map.Entry<Double, Double>> test(
			Classifier<Feature, Category> classifier,
			Dataset<Sample, Category> dataset,
			Finder<Sample, Feature> processor,
			Variant<Feature> variant) {
		Objects.requireNonNull(classifier);
		Objects.requireNonNull(dataset);
		Objects.requireNonNull(processor);
		Objects.requireNonNull(variant);

		Map<Category, Double> truePos = new HashMap<>();
		Map<Category, Double> falsePos = new HashMap<>();
		Map<Category, Double> falseNeg = new HashMap<>();
		for (Category category : dataset.categories()) {
			Collection<Sample> samples = dataset.getSamples(category);
			for (Sample sample : samples) {
				Collection<Feature> raw = processor.process(sample);
				Map<Feature, Double> features = variant.digest(raw);
				Category result = classifier.classify(features);
				if (result == category) {
					truePos.put(result, 1.0 + truePos.getOrDefault(result, 0.0));
				} else {
					falsePos.put(result, 1.0 + falsePos.getOrDefault(result, 0.0));
					falseNeg.put(category, 1.0 + falseNeg.getOrDefault(category, 0.0));
				}
			}
		}
		Map<Category, Map.Entry<Double, Double>> result = new HashMap<>();
		for (Category category : dataset.categories()) {
			Double precision = truePos.getOrDefault(category, 0.0) /
					(truePos.getOrDefault(category, 0.0) + falsePos.getOrDefault(category, 0.0));
			Double recall = truePos.getOrDefault(category, 0.0) /
					(truePos.getOrDefault(category, 0.0) + falseNeg.getOrDefault(category, 0.0));
			result.put(category, new AbstractMap.SimpleImmutableEntry<Double, Double>(precision, recall));
		}
		return result;
	}


}
