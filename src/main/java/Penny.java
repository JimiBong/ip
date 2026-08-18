import java.util.ArrayList;
import java.util.Scanner;

public class Penny {
    public static void main(String[] args) {
        System.out.println("Hi, I'm Penny, what can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            try {
                scanForInput(scanner, list);
            } catch (PennyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void scanForInput(Scanner scanner, ArrayList<Task> list) throws PennyException {
        String input = scanner.nextLine();
        String[] parts = input.split("\\s+", 2); // Split by one or more spaces
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        if (command.isBlank()) {
            throw new PennyException("Sorry I didn't quite catch that.");
        }

        if (command.equalsIgnoreCase("bye")) {
            System.out.println("Bye! See you soon!");
            return;
        }

        else if (command.equalsIgnoreCase("hi") ||
            command.equalsIgnoreCase("hello")) {

            System.out.println("Hi");
            return;
        }

        else if (command.equalsIgnoreCase("list")) {
            if (list.isEmpty()) {
                throw new PennyException("There are no tasks on your list.");
            }

            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }

        else if (command.equalsIgnoreCase("mark")) {
            if (!isInteger(arguments)) {
                throw new PennyException("Mark expects a number");
            }

            int index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= list.size()) {
                throw new PennyException("Mark out of bounds");
            }

            Task task = list.get(index);
            task.markAsDone();
            System.out.println("Marked as done: " + task);
        }

        else if (command.equalsIgnoreCase("unmark")) {
            if (!isInteger(arguments)) {
                throw new PennyException("Unmark expects a number");
            }

            int index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= list.size()) {
                throw new PennyException("Unmark out of bounds");
            }

            Task task = list.get(index);
            task.unmarkAsDone();
            System.out.println("Marked as done: " + task);

        }

        else if (command.equalsIgnoreCase("todo") ||
                command.equalsIgnoreCase("deadline") ||
                command.equalsIgnoreCase("event")) {

            if (arguments.isBlank()) {
                throw new PennyException("Task description cannot be blank");
            }

            Task task = null;

            if (command.equalsIgnoreCase("todo")) {
                task = ToDoTask.create(arguments);
            }
            else if (command.equalsIgnoreCase("deadline")) {
                task = DeadlineTask.create(arguments);
            }
            else if (command.equalsIgnoreCase("event")) {
                task = EventTask.create(arguments);
            }

            list.add(task);
            System.out.println("Added: " + task);

        }
        else {
            throw new PennyException("I don't think I understand.");
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
