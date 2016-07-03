package bayes4j.filters;

import java.util.List;

import bayes4j.Filter;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test suite for {@link NoCommons}.
 */
public class NoCommonsTest {
    private static final Filter<String> NO_COMMONS = new NoCommons();

    private static  final List<String> WORDS = asList("The", "O2", "arena", "has", "20000", "seats");

    @Test
    public void process() throws Exception {
        assertThat(NO_COMMONS.process(WORDS)).containsExactly("O2", "arena", "20000", "seats");
    }
}