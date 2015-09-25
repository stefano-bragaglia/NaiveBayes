package core;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Add some meaningful class description...
 */
public class Learning {

	private static final Logger logger = LoggerFactory.getLogger(Learning.class);

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

		long elapsed = System.nanoTime();
		Classifier.Builder<Feature, Category> builder = new Classifier.Builder<>();
		for (Category category : training.categories()) {
			Collection<Sample> samples = training.getSamples(category);
			for (Sample sample : samples) {
				Map<Feature, Double> features = processor.process(sample);
				builder.add(category, features);
			}
		}
		Classifier<Feature, Category> classifier = builder.build();
		elapsed = System.nanoTime() - elapsed;
		logger.info("The classifier for {} has been trained in {}s", processor, String.format("%.3f", elapsed / 1_000_000_000.0));
		return classifier;
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

		long elapsed = System.nanoTime();
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
					Optional<Category> optional = classifier.classify(features);
					if (optional.isPresent()) {
						Category category = optional.get();
						List<Processor<Sample, Feature>> available = needed.get(category);
						if (available.contains(processors[i])) {
							training.add(category, sample);
							available.remove(processors[i]);
							break;
						}
					}
				}
			}
		}
		elapsed = System.nanoTime() - elapsed;
		logger.info("The classifier for {} has been cotrained in {}s", training, String.format("%.3f", elapsed / 1_000_000_000.0));
		return classifiers.values();
	}

	/**
	 * @param classifier
	 * @param dataset
	 * @param processor
	 * @param <Sample>
	 * @param <Feature>
	 * @param <Category>
	 * @return
	 */
	public static <Sample, Feature, Category> Map<Category, Map.Entry<Double, Double>> test(
			Dataset<Sample, Category> dataset,
			Processor<Sample, Feature> processor,
			Classifier<Feature, Category> classifier) {
		Objects.requireNonNull(classifier);
		Objects.requireNonNull(dataset);
		Objects.requireNonNull(processor);

		long elapsed = System.nanoTime();
		Map<Category, Double> truePos = new HashMap<>();
		Map<Category, Double> falsePos = new HashMap<>();
		Map<Category, Double> falseNeg = new HashMap<>();
		for (Category category : dataset.categories()) {
			Collection<Sample> samples = dataset.getSamples(category);
			for (Sample sample : samples) {
				Map<Feature, Double> features = processor.process(sample);
				Optional<Category> optional = classifier.classify(features);
				if (optional.isPresent()) {
					Category result = optional.get();
					if (result == category) {
						truePos.put(result, 1.0 + truePos.getOrDefault(result, 0.0));
					} else {
						falsePos.put(result, 1.0 + falsePos.getOrDefault(result, 0.0));
						falseNeg.put(category, 1.0 + falseNeg.getOrDefault(category, 0.0));
					}
				} else {
					falseNeg.put(category, 1.0 + falseNeg.getOrDefault(category, 0.0));
				}
			}
		}
		Map<Category, Map.Entry<Double, Double>> result = new TreeMap<>();
		for (Category category : dataset.categories()) {
			Double precision = truePos.getOrDefault(category, 0.0) /
					(truePos.getOrDefault(category, 0.0) + falsePos.getOrDefault(category, 0.0));
			if (Double.isNaN(precision)) {
				precision = 0.0;
			}
			Double recall = truePos.getOrDefault(category, 0.0) /
					(truePos.getOrDefault(category, 0.0) + falseNeg.getOrDefault(category, 0.0));
			if (Double.isNaN(recall)) {
				recall = 0.0;
			}
			result.put(category, new AbstractMap.SimpleImmutableEntry<Double, Double>(precision, recall));
		}
		elapsed = System.nanoTime() - elapsed;
		logger.info("The classifier for {} has been tested in {}s", processor, String.format("%.3f", elapsed / 1_000_000_000.0));
		return result;
	}

}
