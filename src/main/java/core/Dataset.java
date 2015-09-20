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
public class Dataset<Sample, Category> implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(Dataset.class);

	private static final long serialVersionUID = -2209582602204320299L;

	private Map<Category, Set<Sample>> map;

	public Dataset() {
		this.map = new HashMap<>();
	}

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

	public void remove(Category category) {
		Objects.requireNonNull(category);

		map.remove(category);
	}

	public int size() {
		return map.values().stream()
				.mapToInt(Set::size)
				.sum();
	}

	public int size(Category category) {
		Objects.requireNonNull(category);

		if (!map.containsKey(category)) {
			return 0;
		}
		return map.get(category).size();
	}

	public Collection<Category> categories() {
		return map.keySet();
	}

	public Collection<Sample> getSamples(Category category) {
		Objects.requireNonNull(category);

		if (!map.containsKey(category)) {
			return Collections.emptySet();
		}
		return map.get(category);
	}

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
