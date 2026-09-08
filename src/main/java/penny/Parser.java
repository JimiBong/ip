package penny;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
        assert tasks != null : "tasks must not be null";
        assert input != null : "input must not be null";

        String[] parts = input.split("\\s+", 2); // Split by one or more spaces
        Command command = Command.parse(parts[0]);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case BYE:
                return handleBye();
            case LIST:
                return handleList(tasks, isLoading);
            case DUE:
                return handleDue(tasks, arguments, isLoading);
            case FIND:
                return handleFind(tasks, arguments, isLoading);
            case MARK:
                return handleMark(tasks, arguments, isLoading);
            case UNMARK:
                return handleUnmark(tasks, arguments, isLoading);
            case DELETE:
                return handleDelete(tasks, arguments, isLoading);
            case TODO:
                return handleTodo(tasks, arguments, isLoading);
            case DEADLINE:
                return handleDeadline(tasks, arguments, isLoading);
            case EVENT:
                return handleEvent(tasks, arguments, isLoading);
            case UNKNOWN:
                throw new PennyException("I don't think I understand.");
            default:
                return new ParseResult("", false);
        }
    }

    private static ParseResult handleBye() {
        return new ParseResult("Bye! See you soon!", true);
    }

    private static ParseResult handleList(TaskList tasks, boolean isLoading) throws PennyException {
        if (isLoading) {
            return new ParseResult("", false);
        }

        if (tasks.isEmpty()) {
            throw new PennyException("There are no tasks on your list");
        }

        return new ParseResult(formatNumbered(tasks, task -> true), false);
    }

    private static ParseResult handleDue(TaskList tasks, String arguments, boolean isLoading) throws PennyException {
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

        String dueTasks = formatNumbered(tasks, task -> task.isDueOn(dueDate));

        if (dueTasks.isEmpty()) {
            throw new PennyException("There are no tasks due on " + DateTime.format(dueDate));
        }

        return new ParseResult(dueTasks, false);
    }

    private static ParseResult handleFind(TaskList tasks, String arguments, boolean isLoading) throws PennyException {
        if (isLoading) {
            return new ParseResult("", false);
        }

        if (arguments.isEmpty()) {
            throw new PennyException("Find keyword cannot be empty");
        }

        if (tasks.isEmpty()) {
            throw new PennyException("There are no tasks on your list");
        }

        String matchingTasks = formatNumbered(tasks, task -> task.hasKeyword(arguments.trim()));

        if (matchingTasks.isEmpty()) {
            throw new PennyException("No matching tasks on your list");
        }

        return new ParseResult(matchingTasks, false);
    }

    private static ParseResult handleMark(TaskList tasks, String arguments, boolean isLoading) throws PennyException {
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
    }

    private static ParseResult handleUnmark(TaskList tasks, String arguments, boolean isLoading)
            throws PennyException {
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
    }

    private static ParseResult handleDelete(TaskList tasks, String arguments, boolean isLoading)
            throws PennyException {
        if (!isInteger(arguments)) {
            throw new PennyException("Delete expects a number");
        }

        int deleteIndex = Integer.parseInt(arguments) - 1;
        if (deleteIndex < 0 || deleteIndex >= tasks.size()) {
            throw new PennyException("Delete out of bounds");
        }

        Task deletedTask = tasks.remove(deleteIndex);
        return new ParseResult(isLoading ? "" : "Deleted: " + deletedTask, false);
    }

    private static ParseResult handleTodo(TaskList tasks, String arguments, boolean isLoading) throws PennyException {
        Task toDoTask = ToDoTask.create(arguments);
        tasks.add(toDoTask);
        return new ParseResult(isLoading ? "" : "Added: " + toDoTask, false);
    }

    private static ParseResult handleDeadline(TaskList tasks, String arguments, boolean isLoading)
            throws PennyException {
        Task deadlineTask = DeadlineTask.create(arguments);
        tasks.add(deadlineTask);
        return new ParseResult(isLoading ? "" : "Added: " + deadlineTask, false);
    }

    private static ParseResult handleEvent(TaskList tasks, String arguments, boolean isLoading)
            throws PennyException {
        Task eventTask = EventTask.create(arguments);
        tasks.add(eventTask);
        return new ParseResult(isLoading ? "" : "Added: " + eventTask, false);
    }

    private static String formatNumbered(TaskList tasks, Predicate<Task> filter) {
        return IntStream.range(0, tasks.size())
                .filter(i -> filter.test(tasks.get(i)))
                .mapToObj(i -> (i + 1) + ". " + tasks.get(i))
                .collect(Collectors.joining("\n"));
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
