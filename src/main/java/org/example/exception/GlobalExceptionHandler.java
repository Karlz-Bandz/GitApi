package org.example.exception;

import org.example.exception.git.GitErrorResponse;
import org.example.exception.git.GitNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GitNotFoundException.class)
    public ResponseEntity<GitErrorResponse> handleGitUserNotFoundException(GitNotFoundException ex){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        GitErrorResponse gitErrorResponse = GitErrorResponse.builder()
                .status(httpStatus.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(gitErrorResponse, httpStatus);
    }
}
