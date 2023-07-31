
package todolistapp;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    public String taskTitle;
    public String details;
    public LocalDate deadline;
    

    public Task(String taskTitle, String details, LocalDate deadline) {
        this.taskTitle = taskTitle;
        this.details = details;
        this.deadline = deadline;
    }
    
    public String getTaskTitle() {
        return taskTitle;
    }

    public String getDetails() {
        return details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    
}
