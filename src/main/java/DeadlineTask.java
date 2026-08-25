import java.time.LocalDateTime;
import java.util.Locale;

public class DeadlineTask extends Task{
    protected LocalDateTime deadline;

    protected DeadlineTask(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
        this.icon = "D";
    }

    public static DeadlineTask create(String arguments) throws PennyException {
        if (!arguments.contains("/by")) {
            throw new PennyException("Deadlines need '/by'");
        }

        String[] parts = arguments.split("/by", 2);
        String description = parts[0].trim();
        String deadline = parts[1].trim();

        if (description.isBlank() || deadline.isBlank()) {
            throw new PennyException("Deadline description and /by cannot be empty");
        }

        return new DeadlineTask(description, DateTime.parse(deadline));
    }

    @Override
    public boolean isDueOn(LocalDateTime dateTime) {
        return this.deadline.toLocalDate().equals(dateTime.toLocalDate());
    }

    @Override
    public String toString(){
        return super.toString()
                + " (by: " + DateTime.format(this.deadline) + ")";
    }
}
