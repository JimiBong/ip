package penny;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static final Path SAVE_PATH = Paths.get("./");

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
