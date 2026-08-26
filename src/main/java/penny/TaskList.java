package penny;

import java.util.ArrayList;

/**
 * Represents a list of tasks using an ArrayList.
 */
public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Returns boolean if the task list is empty.
     *
     * @return boolean if the task list is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns number of tasks in the list as an int.
     *
     * @return number of tasks in the list as an int.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at index.
     *
     * @param index index of the task to get.
     * @return the task at index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns a task at index after removing it from the list.
     *
     * @param index index of the task to remove.
     * @return the task removed.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }
}
