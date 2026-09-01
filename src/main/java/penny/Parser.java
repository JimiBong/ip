package penny;

import java.util.ArrayList;
import java.time.LocalDateTime;


/**
 * Parses input from user, identifying the command used and the arguments passed behind.
 */
public class Parser {
    /**
     * Processes a line of user input against the given task list.
     *
     * @param tasks tasklist to operate on.
     * @param input string entered by user.
     * @param isLoading boolean if loading in commands from previous sessions.
     * @return the response message and whether Penny should stop running.
     * @throws PennyException If the command arguments are wrongly formatted.
     */
    public static ParseResult handleInput(TaskList tasks, String input, boolean isLoading) throws PennyException {
        String[] parts = input.split("\\s+", 2); // Split by one or more spaces
        Command command = Command.parse(parts[0]);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case BYE:
                return new ParseResult("Bye! See you soon!", true);

            case LIST:
                if (isLoading) {
                    return new ParseResult("", false);
                }

                if (tasks.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                ArrayList<String> listedTasks = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    listedTasks.add((i + 1) + ". " + tasks.get(i));
                }
                return new ParseResult(String.join("\n", listedTasks), false);

            case DUE:
                if (isLoading) {
                    return new ParseResult("", false);
                }

                if (arguments.isBlank()) {
                    throw new PennyException("Due date cannot be empty");
                }

                LocalDateTime dueDate = DateTime.parse(arguments);

                if (tasks.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                ArrayList<String> dueTasks = new ArrayList<>();

                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    if (task.isDueOn(dueDate)) {
                        dueTasks.add((i + 1) + ". " + task);
                    }
                }

                if (dueTasks.isEmpty()) {
                    throw new PennyException("There are no tasks due on " + DateTime.format(dueDate));
                }

                return new ParseResult(String.join("\n", dueTasks), false);

            case FIND:
                if (isLoading) {
                    return new ParseResult("", false);
                }

                if (arguments.isEmpty()) {
                    throw new PennyException("Find keyword cannot be empty");
                }

                if (tasks.isEmpty()) {
                    throw new PennyException("There are no tasks on your list");
                }

                ArrayList<String> matchingTasks = new ArrayList<>();

                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    if (task.hasKeyword(arguments.trim())) {
                        matchingTasks.add((i + 1) + ". " + task);
                    }
                }

                if (matchingTasks.isEmpty()) {
                    throw new PennyException("No matching tasks on your list");
                }

                return new ParseResult(String.join("\n", matchingTasks), false);

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
                return new ParseResult(isLoading ? "" : "Marked as done: " + markedTask, false);

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
                return new ParseResult(isLoading ? "" : "Marked as done: " + unmarkedTask, false);

            case DELETE:
                if (!isInteger(arguments)) {
                    throw new PennyException("Delete expects a number");
                }

                int deleteIndex = Integer.parseInt(arguments) - 1;
                if (deleteIndex < 0 || deleteIndex >= tasks.size()) {
                    throw new PennyException("Delete out of bounds");
                }

                Task deletedTask = tasks.remove(deleteIndex);
                return new ParseResult(isLoading ? "" : "Deleted: " + deletedTask, false);

            case TODO:
                Task toDoTask = ToDoTask.create(arguments);
                tasks.add(toDoTask);
                return new ParseResult(isLoading ? "" : "Added: " + toDoTask, false);

            case DEADLINE:
                Task deadlineTask = DeadlineTask.create(arguments);
                tasks.add(deadlineTask);
                return new ParseResult(isLoading ? "" : "Added: " + deadlineTask, false);

            case EVENT:
                Task eventTask = EventTask.create(arguments);
                tasks.add(eventTask);
                return new ParseResult(isLoading ? "" : "Added: " + eventTask, false);

            case UNKNOWN:
                throw new PennyException("I don't think I understand.");

            default:
                return new ParseResult("", false);
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
