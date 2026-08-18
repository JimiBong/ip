import java.util.ArrayList;
import java.util.Scanner;

public class Penny {
    public static void main(String[] args) {
        System.out.println("Hi, I'm Penny, what can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            try {
                boolean keepRunning = scanForInput(scanner, list);
                if (!keepRunning) {
                    break;
                }
            } catch (PennyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean scanForInput(Scanner scanner, ArrayList<Task> list) throws PennyException {
        String input = scanner.nextLine();
        String[] parts = input.split("\\s+", 2); // Split by one or more spaces
        Command command = Command.parse(parts[0]);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case BYE:
                System.out.println("Bye! See you soon!");
                return false;

            case LIST:
                if (list.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
                return true;

            case MARK:
                if (!isInteger(arguments)) {
                    throw new PennyException("Mark expects a number");
                }

                int markIndex = Integer.parseInt(arguments) - 1;
                if (markIndex < 0 || markIndex >= list.size()) {
                    throw new PennyException("Mark out of bounds");
                }

                Task markedTask = list.get(markIndex);
                markedTask.markAsDone();
                System.out.println("Marked as done: " + markedTask);
                return true;

            case UNMARK:
                if (!isInteger(arguments)) {
                    throw new PennyException("Unmark expects a number");
                }

                int unmarkIndex = Integer.parseInt(arguments) - 1;
                if (unmarkIndex < 0 || unmarkIndex >= list.size()) {
                    throw new PennyException("Unmark out of bounds");
                }

                Task unmarkedTask = list.get(unmarkIndex);
                unmarkedTask.unmarkAsDone();
                System.out.println("Marked as done: " + unmarkedTask);
                return true;

            case DELETE:
                if (!isInteger(arguments)) {
                    throw new PennyException("Delete expects a number");
                }

                int deleteIndex = Integer.parseInt(arguments) - 1;
                if (deleteIndex < 0 || deleteIndex >= list.size()) {
                    throw new PennyException("Delete out of bounds");
                }

                Task deletedTask = list.remove(deleteIndex);
                System.out.println("Deleted: " + deletedTask);
                return true;

            case TODO:
                Task toDoTask = ToDoTask.create(arguments);
                list.add(toDoTask);
                System.out.println("Added: " + toDoTask);
                return true;

            case DEADLINE:
                Task deadlineTask = DeadlineTask.create(arguments);
                list.add(deadlineTask);
                System.out.println("Added: " + deadlineTask);
                return true;

            case EVENT:
                Task eventTask = EventTask.create(arguments);
                list.add(eventTask);
                System.out.println("Added: " + eventTask);
                return true;

            case UNKNOWN:
                throw new PennyException("I don't think I understand.");

            default:
                return true;
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
