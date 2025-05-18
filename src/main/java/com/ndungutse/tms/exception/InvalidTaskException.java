package com.ndungutse.tms.exception;

public class InvalidTaskException extends RuntimeException {
    public InvalidTaskException() {
        super();
    }

    public InvalidTaskException(String message) {
        super(message);
    }
}
