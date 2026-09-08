package penny;

import java.time.LocalDateTime;

/**
 * DeadlineTask with a description and a deadline date.
 */
public class DeadlineTask extends Task {
    private static final String BY_KEYWORD = "/by";

    protected LocalDateTime deadline;

    protected DeadlineTask(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
        this.icon = "D";
    }

    /**
     * Returns DeadlineTask.
     *
     * @param arguments description /by deadline of deadline task.
     * @return DeadlineTask.
     * @throws PennyException if /by is missing, or description or deadline is empty.
     */
    public static DeadlineTask create(String arguments) throws PennyException {
        if (!arguments.contains(BY_KEYWORD)) {
            throw new PennyException("Deadlines need '/by'");
        }

        String[] parts = arguments.split(BY_KEYWORD, 2);
        assert parts.length == 2 : "Splitting on '/by' after the contains() check should always yield 2 parts";
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
    public String toString() {
        return super.toString()
                + " (by: " + DateTime.format(this.deadline) + ")";
    }
}
