package penny;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Parser {
    public static boolean handleInput(TaskList tasks, String input, boolean isLoading) throws PennyException {

        String[] parts = input.split("\\s+", 2); // Split by one or more spaces
        Command command = Command.parse(parts[0]);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case BYE:
                UI.display("Bye! See you soon!");
                return false;

            case LIST:
                if (isLoading) {
                    return true;
                }

                if (tasks.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                for (int i = 0; i < tasks.size(); i++) {
                    UI.display((i + 1) + ". " + tasks.get(i));
                }
                return true;

            case DUE:
                if (isLoading) {
                    return true;
                }

                if (arguments.isBlank()) {
                    throw new PennyException("Due date cannot be empty");
                }

                LocalDateTime dueDate = DateTime.parse(arguments);

                if (tasks.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                ArrayList<String> matchingTasks = new ArrayList<>();

                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    if (task.isDueOn(dueDate)) {
                        matchingTasks.add((i + 1) + ". " + task);
                    }
                }

                if (matchingTasks.isEmpty()) {
                    throw new PennyException("There are no tasks due on " + DateTime.format(dueDate));
                }

                UI.display(String.join("\n", matchingTasks));

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
                if (!isLoading) {
                    UI.display("Marked as done: " + markedTask);
                }
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
                if (!isLoading) {
                    UI.display("Marked as done: " + unmarkedTask);
                }
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
                if (!isLoading) {
                    UI.display("Deleted: " + deletedTask);
                }
                return true;

            case TODO:
                Task toDoTask = ToDoTask.create(arguments);
                tasks.add(toDoTask);
                if (!isLoading) {
                    UI.display("Added: " + toDoTask);
                }
                return true;

            case DEADLINE:
                Task deadlineTask = DeadlineTask.create(arguments);
                tasks.add(deadlineTask);
                if (!isLoading) {
                    UI.display("Added: " + deadlineTask);
                }
                return true;

            case EVENT:
                Task eventTask = EventTask.create(arguments);
                tasks.add(eventTask);
                if (!isLoading) {
                    UI.display("Added: " + eventTask);
                }
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
