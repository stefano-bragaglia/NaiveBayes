package bayes4j;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO Add some meaningful class description...
 */
public class BayesFactoryTest {

    private static final Path DATASET = Paths.get("./build/dataset.ser");

    private static Dataset<Boolean, String> dataset;

    @BeforeClass
    public static void setUpAll() {
        Dataset<String, Boolean> dataset = BayesFactory.newDataset();
        dataset.append(TRUE, asList("TRUE", "True", "true"));
        dataset.append(FALSE, asList("FALSE", "False", "false"));
        dataset.save(DATASET);
    }

    @Test
    public void newDataset() throws Exception {
        Dataset<Boolean, String> dataset = BayesFactory.newDataset();
        assertThat(dataset).isNotNull();
        assertThat(dataset.getCategories()).isEmpty();
    }

    @Test
    public void loadDataset() throws Exception {
        Dataset<String, Boolean> dataset = BayesFactory.loadDataset(DATASET);
        assertThat(dataset).isNotNull();
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE", "False", "false");
    }

}