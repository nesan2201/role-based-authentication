package com.example.empuser.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handle(Exception ex)
  { return ResponseEntity.badRequest().body(ex.getMessage()); }
}