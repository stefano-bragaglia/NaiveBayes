package example;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import core.*;
import core.variants.Multinomial;

/**
 * TODO Add some meaningful class description...
 * <p>
 * Laplace smoother!!!
 */
public class Application {
	public static final Path PATH = Paths.get("./training.dat");

	public static void main(String[] args) throws IOException {
		Processor<Path, String> processor =
				new Processor.Builder<Path, String>(new NaiveBagOfWords(), new Multinomial()).build();

		Classifier<String, Language> classifier = Classifier.load(PATH);
		if (null == classifier) {
			System.out.println("TRAINING:");
			long elapsed = System.nanoTime();
			Dataset<Path, Language> training = prepare("training");
			classifier = Learning.train(training, processor);
			elapsed = System.nanoTime() - elapsed;
			System.out.format("Training completed in %.3f.\n", elapsed / 1_000_000_000.0);
			classifier.save(PATH);
		}
		System.out.println("Classifier successfully loaded!");

		System.out.println("\nCONTROLLING:");
		long elapsed = System.nanoTime();
		Dataset<Path, Language> control = prepare("control");
		Map<Language, Map.Entry<Double, Double>> stats = Learning.test(control, processor, classifier);
		elapsed = System.nanoTime() - elapsed;
		System.out.format("Analysis completed in %.3f.\n", elapsed / 1_000_000_000.0);

		System.out.println("\nRESULTS:");
		for (Language language : stats.keySet()) {
			Map.Entry<Double, Double> values = stats.get(language);
			System.out.format("* %s:   precision %.9f recall %.9f\n", language, values.getKey(), values.getValue());
		}

		System.out.println("\nEXAMPLES:");
		try {
			DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("./data/random/"));
			List<Path> found = new ArrayList<>();
			for (Path path : paths) {
				found.add(path);
			}
			Collections.shuffle(found);
			for (int i = 0; i < 10; i++) {
				Path path = found.get(i);
				Map<String, Double> features = processor.process(path);
				Language language = classifier.classify(features);
				System.out.format("File '%s' is %s\n", path, language);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Dataset<Path, Language> prepare(String type) {
		Objects.requireNonNull(type);
		type = type.trim();
		if (type.isEmpty()) {
			throw new IllegalArgumentException("'type' is empty");
		}
		if (!type.equals("control") && !type.equals("random") && !type.equals("training")) {
			throw new IllegalArgumentException("'type' must be either 'control', 'random' or 'training'");
		}

		long count = 0;
		long elapsed = System.nanoTime();
		Dataset<Path, Language> result = new Dataset<>();
		try {
			for (Language language : Language.values()) {
				String id = language.toString().toLowerCase();
				DirectoryStream<Path> paths =
						Files.newDirectoryStream(Paths.get(String.format("./data/%s/%s/", type, id)));
				for (Path path : paths) {
					result.add(language, path);
					count += 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		elapsed = System.nanoTime() - elapsed;
		System.out.format("%d articles retrieved in %.3f.\n", count, elapsed / 1_000_000_000.0);
		return result;
	}

}
