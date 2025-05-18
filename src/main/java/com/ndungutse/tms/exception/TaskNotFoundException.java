package com.ndungutse.tms.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super();
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
