import java.util.ArrayList;
import java.util.Scanner;

public class Penny {
    public static void main(String[] args) {
        System.out.println("Hi, I'm Penny, what can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split("\\s+", 2); // Split by one or more spaces
            String command = parts[0];
            String value = "";

            if (parts.length == 2) {
                value = parts[1];
            }

            if (command.isBlank()) {
                continue;
            }

            if (command.equalsIgnoreCase("bye")) {
                System.out.println("Bye! See you soon!");
                break;
            }

            else if (command.equalsIgnoreCase("list")) {
                if (list.isEmpty()) {
                    System.out.println("List is empty");
                    continue;
                }

                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
            }

            else if (command.equalsIgnoreCase("mark")) {
                if (!isInteger(value)) {
                    System.out.println("mark expects a number");
                    continue;
                }

                int index = Integer.parseInt(value) - 1;
                if (index < 0 || index >= list.size()) {
                    System.out.println("mark out of bounds");
                    continue;
                }

                Task task = list.get(index);
                if (task.markAsDone()) {
                    System.out.println("marked as done: " + task);
                }
            }

            else if (command.equalsIgnoreCase("unmark")) {
                if (!isInteger(value)) {
                    System.out.println("unmark expects a number");
                    continue;
                }

                int index = Integer.parseInt(value) - 1;
                if (index < 0 || index >= list.size()) {
                    System.out.println("unmark out of bounds");
                    continue;
                }

                Task task = list.get(index);
                if (task.unmarkAsDone()) {
                    System.out.println("marked as done: " + task);
                }
            }

            else if (command.equalsIgnoreCase("add")) {
                Task task = new Task(value);
                list.add(task);
                System.out.println("added: " + task);
            }

            else {
                System.out.println("invalid command");
            }
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
