package com.esde.webdevtask1.repository.exception;

public class RepositoryException extends Exception {

    public RepositoryException() {

    }

    public RepositoryException(String message) {
        super("Repository have encountered an error: " + message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
