package bayes4j.extractors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import bayes4j.Extractor;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link WordsFromPath}.
 */
public class WordsFromPathTest {
    private static final Extractor<String, Path> WORDS_FROM_PATH = new WordsFromPath();

    private static final Path INVALID_PATH = Paths.get("/build/build/build/build/build/file.txt");

    private static final Path VALID_PATH_0 = Paths.get("./build/file0.txt");
    private static final Path VALID_PATH_1 = Paths.get("./build/file1.txt");
    private static final Path VALID_PATH_2 = Paths.get("./build/file2.txt");

    private static final String SENTENCE_0 = "";
    private static final String SENTENCE_1 = "This is a neat example: it contains words, but also some symbol!";
    private static final String SENTENCE_2 = "Another ad-hoc tricky example!";

    @BeforeClass
    public static void setUpAll() throws Exception {
        Files.write(VALID_PATH_0, SENTENCE_0.getBytes());
        Files.write(VALID_PATH_1, SENTENCE_1.getBytes());
        Files.write(VALID_PATH_2, SENTENCE_2.getBytes());
    }

    @Test(expected = NullPointerException.class)
    public void error1() throws Exception {
        assertThat(WORDS_FROM_PATH.process(null)).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void error2() throws Exception {
        assertThat(WORDS_FROM_PATH.process(INVALID_PATH)).isEmpty();
    }

    @Test
    public void process0() throws Exception {
        assertThat(WORDS_FROM_PATH.process(VALID_PATH_0)).isEmpty();
    }

    @Test
    public void process1() throws Exception {
        assertThat(WORDS_FROM_PATH.process(VALID_PATH_1))
                .containsExactly("This", "is", "a", "neat", "example",
                                 "it", "contains", "words", "but", "also", "some", "symbol");
    }

    @Test
    public void process2() throws Exception {
        assertThat(WORDS_FROM_PATH.process(VALID_PATH_2))
                .containsExactly("Another", "ad", "hoc", "tricky", "example");
    }

}