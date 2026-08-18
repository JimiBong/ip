public class ToDoTask extends Task {

    protected ToDoTask(String description) {
        super(description);
        this.icon = "T";
    }

    public static ToDoTask create(String arguments) {
        if (arguments.isBlank()) {
            System.out.println("todo description cannot be empty.");
            return null;
        }
        return new ToDoTask(arguments);
    }
}
