package penny;

/**
 * TodoTask with only a description.
 */
public class ToDoTask extends Task {
    protected ToDoTask(String arguments) {
        super(arguments.trim());
        this.icon = "T";
    }

    /**
     * Returns TodoTask.
     *
     * @param arguments description of TodoTask.
     * @return Todotask.
     * @throws PennyException if the description is empty.
     */
    public static ToDoTask create(String arguments) throws PennyException {
        if (arguments.isBlank()) {
            throw new PennyException("Todo description cannot be empty");
        }
        return new ToDoTask(arguments);
    }
}
