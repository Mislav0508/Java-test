package com.javatest.test.service.errors;

import com.javatest.test.viewmodel.exceptions.ResourceNotFoundException;
import com.javatest.test.viewmodel.response.ApiResponse;
import com.javatest.test.viewmodel.response.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

   @Autowired
   private LogWriterService logWriterService;

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
      logWriterService.writeException(ex);
      Meta meta = new Meta(false, System.currentTimeMillis());
      return new ResponseEntity<>(new ApiResponse<>(meta, null, ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponse<List<String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
      logWriterService.writeException(ex);
      List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                    .map(FieldError::getDefaultMessage)
                                    .collect(Collectors.toList());
      Meta meta = new Meta(false, System.currentTimeMillis());
      return new ResponseEntity<>(new ApiResponse<>(meta, null, errors, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
      logWriterService.writeException(ex);
      Meta meta = new Meta(false, System.currentTimeMillis());
      return new ResponseEntity<>(new ApiResponse<>(meta, null, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
