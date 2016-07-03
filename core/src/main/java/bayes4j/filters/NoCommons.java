package bayes4j.filters;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import bayes4j.Filter;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * A filter that removes the common words from the given collection of features (case insensitive).
 */
public final class NoCommons implements Filter<String> {
    private static final long serialVersionUID = 3741535708400407900L;

    private static final TreeSet<String> COMMONS = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public Collection<String> process(Collection<String> features) {
        return requireNonNull(features).stream()
                                       .filter(feature -> !COMMONS.contains(feature))
                                       .collect(toList());
    }

    static {
        COMMONS.addAll(Arrays.asList("the", "be", "am", "are", "is", "was", "were", "being", "been", "to", "of", "and",
                                     "a", "an", "in", "that", "have", "has", "having", "had", "I", "it", "for", "not",
                                     "on", "with", "he", "as", "you", "do", "does", "did", "doing", "done", "at",
                                     "this", "but", "his", "by", "from", "they", "we", "say", "says", "saying", "said",
                                     "her", "she", "or", "an", "will", "my", "one", "all", "would", "there", "their",
                                     "what", "so", "up", "out", "if", "about", "who", "download", "downloads",
                                     "downloading", "downloaded", "which", "go", "goes", "going", "gone", "me", "when",
                                     "make", "makes", "making", "made", "can", "like", "likes", "liking", "liked",
                                     "time", "times", "timing", "timed", "no", "just", "him", "know", "knows",
                                     "knowing", "knew", "take", "takes", "taking", "took", "taken", "person",
                                     "persons", "into", "year", "years", "your", "good", "goods", "some", "could",
                                     "them", "see", "sees", "seeing", "saw", "seen", "other", "than", "then", "now",
                                     "look", "looks", "looking", "looked", "only", "come", "comes", "coming", "came",
                                     "its", "over", "think", "thinks", "thinking", "thought", "also", "back", "backs",
                                     "after", "use", "uses", "using", "used", "two", "how", "our", "work", "works",
                                     "working", "worked", "first", "well", "wells", "way", "ways", "even", "new",
                                     "want", "wants", "wanting", "wanted", "because", "any", "these", "give", "gives",
                                     "giving", "gave", "given", "day", "days", "most", "us"));
    }

}
