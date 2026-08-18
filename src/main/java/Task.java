public class Task {
    protected String description;
    protected boolean isDone;
    protected String icon = "";

    protected Task(String description) {
        this.description = description.trim();
        this.isDone = false;
        this.icon = "";
    }

    public void markAsDone() throws PennyException{
        if (isDone) {
            throw new PennyException("Task is already marked as done");
        }
        isDone = true;
    }

    public void unmarkAsDone() throws PennyException{
        if (!isDone) {
            throw new PennyException("Task is already marked as done");
        }
        isDone = false;
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
