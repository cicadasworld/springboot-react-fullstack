package cn.jin.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoAPIException.class)
    public ResponseEntity<ErrorResponse> handleTodoAPIException(TodoAPIException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            e.getStatus().value(),
            e.getMessage(),
            webRequest.getDescription(true).substring(4)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
