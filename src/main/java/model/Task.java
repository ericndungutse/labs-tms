package com.ndungutse.task.model;

import java.time.LocalDate;

public class Task {
        private int id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private String status; // "Pending" or "Completed"

        public Task() {}

        public Task(String title, String description, LocalDate dueDate, String status) {
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.status = status;
        }

        // Getters and setters...


}
