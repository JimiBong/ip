package penny;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Loads in commands from previous sessions, and keeps scanning for user input.
 */
public class Penny {
    private static final String FILE_NAME = "commands.txt";

    static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();

        // Load tasks
        String save = FileManager.readData(FILE_NAME);
        if (!save.isEmpty()) {
            String[] savedCommands = save.split("\n");
            for (String command : savedCommands) {
                try {
                    Parser.handleInput(taskList, command, true);
                    commands.add(command);
                } catch (PennyException e) {
                    System.out.println("Error loading command: " + e.getMessage());
                }
            }
            System.out.println("I have loaded your tasks from our previous session.");
        }

        System.out.println("Hi, I'm Penny, what can I do for you?");

        // Handle input
        while (true) {
            try {
                String input = scanner.nextLine();
                boolean isRunning = Parser.handleInput(taskList, input, false);
                if (!isRunning) {
                    break;
                } else {
                    commands.add(input); // Input is a valid command that is not bye
                    FileManager.writeData(FILE_NAME, String.join("\n", commands)); // Save commands
                }
            } catch (PennyException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
