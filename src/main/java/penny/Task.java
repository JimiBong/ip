package penny;

import java.time.LocalDateTime;

public class Task {
    protected String description;
    protected boolean isDone;
    protected String icon = "";

    protected Task(String description) {
        this.description = description;
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

    public boolean isDueOn(LocalDateTime dateTime) {
        return false;
    }

    /**
     * Returns boolean on whether the task toString contains keyword.
     *
     * @param keyword string keyword to check for.
     * @return boolean on whether the task toString contains keyword.
     */
    public boolean hasKeyword(String keyword) {
        return toString().contains(keyword);
    }

    @Override
    public String toString() {
        String doneIcon = isDone ? "X" : " ";
        return "[" + icon + "][" + doneIcon + "] " + description;
    }
}
