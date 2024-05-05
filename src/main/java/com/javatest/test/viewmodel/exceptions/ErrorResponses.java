package com.javatest.test.viewmodel.exceptions;

import lombok.Data;

@Data
public class ErrorResponses {

   public static final String NOT_FOUND_RESPONSE = "{\"meta\":{\"success\":false,\"timestamp\":\"1591076923000\"},\"data\":null,\"message\":\"User with ID not found\",\"code\":404}";

   public static final String BAD_REQUEST_RESPONSE = "{\"meta\":{\"success\":false,\"timestamp\":\"1591076923000\"},\"data\":null,\"message\":\"One error string or an array of error strings.\",\"code\":400}";

   public static final String SERVER_ERROR_RESPONSE = "{\"meta\":{\"success\":false,\"timestamp\":\"1591076923000\"},\"data\":null,\"message\":\"Internal server error.\",\"code\":500}";

}
