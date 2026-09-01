package penny;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads in commands from previous sessions, and answers user input either via
 * the CLI ({@link #main}) or the GUI ({@code MainWindow}, via {@link #respond}).
 */
public class Penny {
    private static final String FILE_NAME = "commands.txt";

    private final TaskList taskList = new TaskList();
    private final ArrayList<String> commands = new ArrayList<>();

    /**
     * Creates a Penny instance, loading tasks saved from a previous session.
     */
    public Penny() {
        String save = FileManager.readData(FILE_NAME);
        if (!save.isEmpty()) {
            for (String command : save.split("\n")) {
                try {
                    Parser.handleInput(taskList, command, true);
                    commands.add(command);
                } catch (PennyException e) {
                    System.out.println("Error loading command: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Processes one line of user input: runs it against the task list, persists it if it
     * wasn't the exit command, and turns any {@link PennyException} into an error message
     * instead of propagating it. Package-private so the GUI controller can act on
     * {@link ParseResult#shouldExit()} as well as the message text.
     */
    ParseResult respond(String input) {
        try {
            ParseResult result = Parser.handleInput(taskList, input, false);
            if (!result.shouldExit()) {
                commands.add(input);
                FileManager.writeData(FILE_NAME, String.join("\n", commands));
            }
            return result;
        } catch (PennyException e) {
            return new ParseResult(e.getMessage(), false);
        }
    }

    public static void main(String[] args) {
        Penny penny = new Penny();
        Scanner scanner = new Scanner(System.in);

        if (!penny.commands.isEmpty()) {
            UI.display("I have loaded your tasks from our previous session.");
        }
        UI.display("Hi, I'm Penny, what can I do for you?");

        while (true) {
            String input = scanner.nextLine();
            ParseResult result = penny.respond(input);
            UI.display(result.message());
            if (result.shouldExit()) {
                break;
            }
        }
    }
}
