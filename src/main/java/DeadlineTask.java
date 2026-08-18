public class DeadlineTask extends Task{
    protected String  deadline;

    protected DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
        this.icon = "D";
    }

    public static DeadlineTask create(String arguments) {
        if (!arguments.contains("/by")) {
            System.out.println("deadline requires '/by'");
            return null;
        }

        String[] parts = arguments.split("/by", 2);
        String description = parts[0].trim();
        String deadline = parts[1].trim();

        if (description.isBlank() || deadline.isBlank()) {
            System.out.println("description and deadline cannot be empty.");
            return null;
        }

        return new DeadlineTask(description, deadline);
    }

    @Override
    public String toString(){
        return super.toString() + " (by: " + this.deadline + ")";
    }
}
