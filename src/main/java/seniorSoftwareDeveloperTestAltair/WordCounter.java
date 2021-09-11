package seniorSoftwareDeveloperTestAltair;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class WordCounter {
    private static final String ELEMENT_OUTPUT = "-  Word: %s  >> Occurrences: %s %n";

    private final PrintStream printStream;

    /**
     * Creates a new WordCounter, using {@link System#out} as default print stream.
     */
    public WordCounter() {
        this(System.out);
    }

    /**
     * Creates a new WordCounter, specifying the print stream to use for output.
     *
     * @param printStream the print stream to use
     */
    public WordCounter(PrintStream printStream) {
        this.printStream = printStream;
    }

    /**
     * Counts the number of occurrences of each word in the given lines, and prints them ordered from more to less frequent with an occurrence count aside.
     *
     * @param lines the list of lines containing the words to count
     */
    public void countWords(List<String> lines) {
        if (lines == null) return;

        // we will need a map that supports concurrency, because lines will be processed in parallel
        // also, using a LongAdder will have an optimal performance later, while counting words in multiple threads
        Map<String, LongAdder> wordMap = new ConcurrentHashMap<>();

        // process each line, in parallel
        lines.parallelStream()
                .filter(Objects::nonNull) // avoid null lines
                .forEach(line ->
                        // split the line into words, using punctuation and spacing characters as separators
                        Arrays.stream(line.split("[\\p{Punct}\\s+]"))
                                .filter(w -> !w.isBlank()) // filter out blank words
                                .map(String::toLowerCase) // convert words to lowercase
                                .forEach(word ->
                                        // adds a new entry in the map for each word, if it doesn't exist
                                        wordMap.computeIfAbsent(word, value -> new LongAdder())
                                                // increment the counter, works perfectly in multithreaded environment
                                                .increment()));

        // preparing a comparator based on the stored count for each word
        Comparator<Map.Entry<String, LongAdder>> comparator = Comparator.comparing(entry -> entry.getValue().longValue());

        // iterating the stream of entries in the word map
        wordMap.entrySet().stream()
                // using the reversed comparator to sort the entries
                .sorted(comparator.reversed())
                // and printing the result to the print stream, with guarantee that the stream is processed in order
                .forEachOrdered(entry -> printStream.printf(ELEMENT_OUTPUT, entry.getKey(), entry.getValue().longValue()));
    }
}
