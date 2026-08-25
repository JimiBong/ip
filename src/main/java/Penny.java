import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Penny {
    private static final String FILE_NAME = "commands.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<String> commands = new ArrayList<>();

        // Load tasks
        String save = FileManager.readData(FILE_NAME);
        System.out.println(save);
        if (!save.isEmpty()) {
            String[] savedCommands = save.split("\n");
            for (String command : savedCommands) {
                try {
                    handleInput(tasks, command, true);
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
                boolean keepRunning = handleInput(tasks, input, false);
                if (!keepRunning) {
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

    private static boolean handleInput(ArrayList<Task> tasks, String input, boolean loading) throws PennyException {

        String[] parts = input.split("\\s+", 2); // Split by one or more spaces
        Command command = Command.parse(parts[0]);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case BYE:
                System.out.println("Bye! See you soon!");
                return false;

            case LIST:
                if (loading) {
                    return true;
                }

                if (tasks.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                return true;

            case MARK:
                if (!isInteger(arguments)) {
                    throw new PennyException("Mark expects a number");
                }

                int markIndex = Integer.parseInt(arguments) - 1;
                if (markIndex < 0 || markIndex >= tasks.size()) {
                    throw new PennyException("Mark out of bounds");
                }

                Task markedTask = tasks.get(markIndex);
                markedTask.markAsDone();
                displayText("Marked as done: " + markedTask, loading);
                return true;

            case UNMARK:
                if (!isInteger(arguments)) {
                    throw new PennyException("Unmark expects a number");
                }

                int unmarkIndex = Integer.parseInt(arguments) - 1;
                if (unmarkIndex < 0 || unmarkIndex >= tasks.size()) {
                    throw new PennyException("Unmark out of bounds");
                }

                Task unmarkedTask = tasks.get(unmarkIndex);
                unmarkedTask.unmarkAsDone();
                displayText("Marked as done: " + unmarkedTask, loading);
                return true;

            case DELETE:
                if (!isInteger(arguments)) {
                    throw new PennyException("Delete expects a number");
                }

                int deleteIndex = Integer.parseInt(arguments) - 1;
                if (deleteIndex < 0 || deleteIndex >= tasks.size()) {
                    throw new PennyException("Delete out of bounds");
                }

                Task deletedTask = tasks.remove(deleteIndex);
                displayText("Deleted: " + deletedTask, loading);
                return true;

            case TODO:
                Task toDoTask = ToDoTask.create(arguments);
                tasks.add(toDoTask);
                displayText("Added: " + toDoTask, loading);
                return true;

            case DEADLINE:
                Task deadlineTask = DeadlineTask.create(arguments);
                tasks.add(deadlineTask);
                displayText("Added: " + deadlineTask, loading);
                return true;

            case EVENT:
                Task eventTask = EventTask.create(arguments);
                tasks.add(eventTask);
                displayText("Added: " + eventTask, loading);
                return true;

            case UNKNOWN:
                throw new PennyException("I don't think I understand.");

            default:
                return true;
        }
    }

    public static void displayText(String text, boolean loading) {
        if (!loading) {
            System.out.println(text);
        }
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
