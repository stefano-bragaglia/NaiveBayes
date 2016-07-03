package bayes4j.filters;

import java.util.List;

import bayes4j.Filter;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test suite for {@link NoNumbers}.
 */
public class NoNumbersTest {
    private static final Filter<String> NO_NUMBERS = new NoNumbers();

    private static  final List<String> WORDS = asList("The", "O2", "arena", "hosts", "20000", "guests");

    @Test
    public void process() throws Exception {
        assertThat(NO_NUMBERS.process(WORDS)).containsExactly("The", "O2", "arena", "hosts", "guests");
    }
}