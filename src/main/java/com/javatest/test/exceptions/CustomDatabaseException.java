package com.javatest.test.exceptions;

public class CustomDatabaseException extends RuntimeException {
   public CustomDatabaseException(String message, Throwable cause) {
      super(message, cause);
   }
}