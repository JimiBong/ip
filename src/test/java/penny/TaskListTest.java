package penny;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    private TaskList tasklist;
    private Task dummyTask1;
    private Task dummyTask2;

    @BeforeEach
    void setUp() {
        tasklist = new TaskList();
        dummyTask1 = new ToDoTask("Read book");
        dummyTask2 = new ToDoTask("Return book");
    }

    @Test
    @DisplayName("isEmpty returns true for a newly instantiated list")
    void isEmpty_newList_returnsTrue() {
        assertTrue(tasklist.isEmpty());
    }

    @Test
    @DisplayName("isEmpty returns false when list contains tasks")
    void isEmpty_listWithTask_returnsFalse() {
        tasklist.add(dummyTask1);

        assertFalse(tasklist.isEmpty());
    }

    @Test
    @DisplayName("size returns zero for a newly instantiated list")
    void size_emptyList_returnsZero() {
        assertEquals(0, tasklist.size());
    }

    @Test
    @DisplayName("add increases the size of the list and appends task")
    void add_singleTask_increasesSize() {
        tasklist.add(dummyTask1);

        assertEquals(1, tasklist.size());
    }

    @Test
    @DisplayName("get retrieves the correct task at specified index")
    void get_validIndex_returnsCorrectTask() {
        tasklist.add(dummyTask1);
        tasklist.add(dummyTask2);

        assertEquals(dummyTask2, tasklist.get(1));
    }

    @Test
    @DisplayName("get throws IndexOutOfBoundsException when accessing invalid index")
    void get_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> tasklist.get(0));
    }

    @Test
    @DisplayName("remove deletes the task at specified index and returns it")
    void remove_validIndex_removesAndReturnsTask() {
        tasklist.add(dummyTask1);
        tasklist.add(dummyTask2);

        Task removedTask = tasklist.remove(0);

        assertEquals(dummyTask1, removedTask);
        assertEquals(1, tasklist.size());
        assertEquals(dummyTask2, tasklist.get(0));
    }

    @Test
    @DisplayName("remove throws IndexOutOfBoundsException when deleting non-existent index")
    void remove_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> tasklist.remove(0));
    }
}