package bayes4j;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link Dataset}.
 */
public class DatasetImplTest {

    private static final Path DATASET = Paths.get("./build/dataset.ser");

    private static Dataset<String, Boolean> dataset;

    @Before
    public void setUp() throws Exception {
        dataset = BayesFactory.newDataset();
        dataset.append(TRUE, asList("TRUE", "True", "true"));
        dataset.append(FALSE, "FALSE");
    }

    @Test
    public void appendOneExisting() throws Exception {
        dataset.append(FALSE, "FALSE");
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE");
    }

    @Test
    public void appendOneNew() throws Exception {
        dataset.append(FALSE, "False");
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE", "False");
    }

    @Test
    public void appendManyExisting() throws Exception {
        dataset.append(TRUE, asList("TRUE", "True", "true"));
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE");
    }

    @Test
    public void appendManyNew() throws Exception {
        dataset.append(FALSE, asList("False", "false"));
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE", "False", "false");
    }

    @Test
    public void appendManyMixed() throws Exception {
        dataset.append(FALSE, asList("FALSE", "False"));
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE", "False");
    }

    @Test
    public void getCategories() throws Exception {
        assertThat(dataset.getCategories()).containsOnly(TRUE, FALSE);
    }

    @Test
    public void getSamplesTRUE() throws Exception {
        assertThat(dataset.getSamples(TRUE)).containsOnly("TRUE", "True", "true");
    }

    @Test
    public void getSamplesFALSE() throws Exception {
        assertThat(dataset.getSamples(FALSE)).containsOnly("FALSE");
    }

    @Test
    public void size() throws Exception {
        assertThat(dataset.size()).isEqualTo(4);
    }

    @Test
    public void save() throws Exception {
        dataset.save(DATASET);
        Dataset<Object, Object> loadedDataset = BayesFactory.loadDataset(DATASET);
        assertThat(loadedDataset).isEqualTo(dataset);
    }

}