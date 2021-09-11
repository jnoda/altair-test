package seniorSoftwareDeveloperTestAltair;

import seniorSoftwareDeveloperTestAltair.fileUtils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main execution class.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FileUtils.getResourcesFilePath("Text.txt")));

        WordCounter wordCounter = new WordCounter();
        wordCounter.countWords(lines);
    }
}
