package seniorSoftwareDeveloperTestAltair.fileUtils;

import java.io.File;

/**
 * File utilities.
 *
 * <p>Class is final to prevent inheritance.</p>
 */
public final class FileUtils {
    private static final String PATH = "src/main/resources/%s";
    private static final String TEST_PATH = "src/test/resources/%s";

    /**
     * Private constructor to avoid accidental instantiation.
     */
    private FileUtils() {
    }

    public static String getResourcesFilePath(String name) {
        return new File(String.format(PATH, name)).getAbsolutePath();
    }

    public static String getResourcesTestFilePath(String name) {
        return new File(String.format(TEST_PATH, name)).getAbsolutePath();
    }
}

