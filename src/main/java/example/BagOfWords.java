package example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import api.Finder;

/**
 * TODO Add some meaningful class description...
 */
public class BagOfWords implements Finder<Path, String> {

	@Override
	public Collection<String> process(Path path) {
		Objects.requireNonNull(path);

		List<String> result = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				for (String word : line.split("\\P{LD}+")) {
					if (!word.matches("^\\d+$")) {
						result.add(word);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
