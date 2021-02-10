package org.example.app.exceptions;

public class FileWriteException extends Exception {

    private final String message;

    public FileWriteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
