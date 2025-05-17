package com.ndungutse.tms.dot;

import java.time.LocalDate;
import java.util.UUID;

public class TaskDTO {
        private UUID id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private String status; // "Pending" or "Completed"

    public TaskDTO() {}

    public TaskDTO(String title, String description, LocalDate dueDate, String status) {
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.status = status;
        }

  public TaskDTO(UUID id, String title, String description, LocalDate dueDate, String status) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.status = status;
        }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                '}';
    }
}
