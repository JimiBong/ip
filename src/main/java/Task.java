public class Task {
    protected String description;
    protected boolean isDone;
    protected String icon = "";

    protected Task(String description) {
        this.description = description.trim();
        this.isDone = false;
        this.icon = "";
    }

    public boolean markAsDone() {
        if (isDone) {
            System.out.println("task already marked as done");
            return false;
        }
        isDone = true;
        return true;
    }

    public boolean unmarkAsDone() {
        if (!isDone) {
            System.out.println("task already unmarked as done");
            return false;
        }

        isDone = false;
        return true;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        String doneIcon = isDone ? "X" : " ";
        return "[" + icon + "][" + doneIcon + "] " + description;
    }
}
