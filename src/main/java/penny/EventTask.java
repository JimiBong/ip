package penny;

import java.time.LocalDateTime;

/**
 * EventTask with a description, a from date and a to date.
 */
public class EventTask extends Task{
    private static final String FROM_KEYWORD = "/from";
    private static final String TO_KEYWORD = "/to";

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
        int fromIndex = arguments.indexOf(FROM_KEYWORD);
        int toIndex = arguments.indexOf(TO_KEYWORD);

        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            throw new PennyException("Events need '/from' followed by '/to'");
        }
        assert toIndex > fromIndex : "'/to' should occur after '/from' at this point";

        String description = arguments.substring(0, fromIndex).trim();

        String remainder = arguments.substring(fromIndex + FROM_KEYWORD.length());
        String[] parts = remainder.split(TO_KEYWORD, 2);
        assert parts.length == 2 : "Splitting on '/to' after the indexOf() check should always yield 2 parts";

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
