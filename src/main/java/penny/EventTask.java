package penny;

import java.time.LocalDateTime;

public class EventTask extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;

    protected EventTask(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.icon = "E";
    }

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
