package bayes4j.extractors;

import bayes4j.Extractor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link WordsFromString}.
 */
public class WordsFromStringTest {
    private static final Extractor<String, String> WORDS_FROM_STRING = new WordsFromString();

    private static final String SENTENCE_1 = "This is a neat example: it contains words, but also some symbol!";
    private static final String SENTENCE_2 = "Another ad-hoc tricky example!";

    @Test(expected = NullPointerException.class)
    public void error() throws Exception {
        assertThat(WORDS_FROM_STRING.process(null)).isEmpty();
    }

    @Test
    public void process0() throws Exception {
        assertThat(WORDS_FROM_STRING.process("")).isEmpty();
    }

    @Test
    public void process1() throws Exception {
        assertThat(WORDS_FROM_STRING.process(SENTENCE_1))
                .containsExactly("This", "is", "a", "neat", "example",
                                 "it", "contains", "words", "but", "also", "some", "symbol");
    }

    @Test
    public void process2() throws Exception {
        assertThat(WORDS_FROM_STRING.process(SENTENCE_2))
                .containsExactly("Another", "ad", "hoc", "tricky", "example");
    }

}