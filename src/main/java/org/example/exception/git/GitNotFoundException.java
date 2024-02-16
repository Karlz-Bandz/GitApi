package org.example.exception.git;

public class GitNotFoundException extends RuntimeException {
    public GitNotFoundException(String message) {
        super(message);
    }
}
