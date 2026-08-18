public class EventTask extends Task{
    protected String from;
    protected String to;

    protected EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.icon = "E";
    }

    public static EventTask create(String arguments) {
        int fromIndex = arguments.indexOf("/from");
        int toIndex = arguments.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            System.out.println("event requires '/from' followed by '/to'");
            return null;
        }

        String description = arguments.substring(0, fromIndex).trim();

        String remainder = arguments.substring(fromIndex + 5);
        String[] parts = remainder.split("/to", 2);

        String from = parts[0].trim();
        String to = parts[1].trim();

        if (description.isBlank() || from.isBlank() || to.isBlank()) {
            System.out.println("description, /from, and /to cannot be empty.");
            return null;
        }

        return new EventTask(description, from, to);
    }

    @Override
    public String toString(){
        return super.toString() + " (from: " + this.from + " | to: " + this.to + ")";
    }
}
