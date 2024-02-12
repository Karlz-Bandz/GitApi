package org.example.exception;

import org.example.exception.git.GitErrorResponse;
import org.example.exception.git.GitUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GitUserNotFoundException.class)
    public ResponseEntity<Object> handleGitUserNotFoundException(GitUserNotFoundException ex){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        GitErrorResponse gitErrorResponse = GitErrorResponse.builder()
                .status(httpStatus.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(gitErrorResponse, httpStatus);
    }
}
