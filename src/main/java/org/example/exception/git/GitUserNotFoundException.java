package org.example.exception.git;

public class GitUserNotFoundException extends RuntimeException{
    public GitUserNotFoundException(String message){
        super(message);
    }
}
