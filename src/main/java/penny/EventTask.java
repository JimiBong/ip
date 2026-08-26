package penny;

import java.time.LocalDateTime;

/**
 * EventTask with a description, a from date and a to date.
 */
public class EventTask extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;

    protected EventTask(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.icon = "E";
    }

    /**
     * Returns EventTask.
     *
     * @param arguments description /from from /to to of EventTask.
     * @return Eventtask.
     * @throws PennyException if /from or /to is missing, or description or from or to is empty.
     */
    public static EventTask create(String arguments) throws PennyException {
        int fromIndex = arguments.indexOf("/from");
        int toIndex = arguments.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            throw new PennyException("Events need '/from' followed by '/to'");
        }

        String description = arguments.substring(0, fromIndex).trim();

        String remainder = arguments.substring(fromIndex + 5);
        String[] parts = remainder.split("/to", 2);

        String from = parts[0].trim();
        String to = parts[1].trim();

        if (description.isBlank() || from.isBlank() || to.isBlank()) {
            throw new PennyException("Event description, /from, and /to cannot be empty");
        }

        return new EventTask(description, DateTime.parse(from), DateTime.parse(to));
    }

    @Override
    public boolean isDueOn(LocalDateTime dateTime) {
        return this.from.toLocalDate().equals(dateTime.toLocalDate());
    }

    @Override
    public String toString(){
        return super.toString()
                + " (from: " + DateTime.format(this.from)
                + " | to: " + DateTime.format(this.to) + ")";
    }
}
