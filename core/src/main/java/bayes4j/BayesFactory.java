package bayes4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A factory for <b>Bayes4j</b> entities.
 */
public final class BayesFactory {

    private BayesFactory() {
    }

    /**
     * Creates an empty instance of {@link Dataset}.
     *
     * @param <Sample>   an observation within a population containing a collection of distinctive features
     * @param <Category> a class that collects a population of observations that share a common property
     *
     * @return an empty instance of {@link Dataset}
     */
    public static <Sample, Category> Dataset<Sample, Category> newDataset() {
        return new DatasetImpl<>();
    }

    /**
     * Loads a {@link Dataset} from the given {@code path}.
     *
     * @param path       the {@link Path} of the {@link Dataset} to load
     * @param <Sample>   an observation within a population containing a collection of distinctive features
     * @param <Category> a class that collects a population of observations that share a common property
     *
     * @return the {@link Dataset} at the given {@code path}
     */
    public static <Sample, Category> Dataset<Sample, Category> loadDataset(Path path) {
        requireNonNull(path);

        Dataset<Sample, Category> result = null;
        try {
            InputStream stream = Files.newInputStream(path);
            ObjectInputStream output = new ObjectInputStream(stream);
            Object object = output.readObject();
            result = (Dataset<Sample, Category>) output.readObject();
            output.close();
            stream.close();
            return result;
        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalArgumentException("'path' is not valid: " + path, e);
        }
    }
}
