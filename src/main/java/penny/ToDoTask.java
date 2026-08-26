package penny;

public class ToDoTask extends Task {
    protected ToDoTask(String arguments) {
        super(arguments.trim());
        this.icon = "T";
    }

    public static ToDoTask create(String arguments) throws PennyException {
        if (arguments.isBlank()) {
            throw new PennyException("Todo description cannot be empty");
        }
        return new ToDoTask(arguments);
    }
}
