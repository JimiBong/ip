package penny;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles reading and writing of data to files at a relative path.
 */
public class FileManager {
    private static final Path SAVE_PATH = Paths.get("./");

    /**
     * Create and write files.
     *
     * @param fileName name of created file.
     * @param content content to write to the created file.
     */
    public static void writeData(String fileName, String content) {
        Path filePath = SAVE_PATH.resolve(fileName);

        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            Files.writeString(filePath, content);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath + " | Error: " + e.getMessage());
        }
    }

    /**
     * Returns contents of the fileName as a string,
     * if not found, returns "".
     *
     * @param fileName name of file to look for.
     * @return contents of the file if found as a string.
     */
    public static String readData(String fileName) {
        Path filePath = SAVE_PATH.resolve(fileName);

        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
                return ""; // Return empty string for newly created file
            }

            return Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + filePath + " | Error: " + e.getMessage());
            return "";
        }
    }
}
