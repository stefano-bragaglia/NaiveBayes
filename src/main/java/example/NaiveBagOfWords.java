package example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import core.Analyser;

/**
 * TODO Add some meaningful class description...
 */
public class NaiveBagOfWords implements Analyser<Path, String> {

	@Override
	public Collection<String> process(Path path) {
		Objects.requireNonNull(path);

		List<String> result = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				line = line.toLowerCase();
				line = line.replaceAll("[,;\\.!?:_\"']", " ");
				line = line.replaceAll("↑", "*");
				String temp;
				do {
					temp = line;
					line = temp.replaceAll("  ", " ");
				} while (!temp.equals(line));
				String[] words = line.split(" ");
				Collections.addAll(result, words);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
