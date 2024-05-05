package com.javatest.test.viewmodel.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiTestResponse<T> {

   @Schema(description = "Metadata concerning the response")
   Meta meta;

   @Schema(description = "The data payload of the response, can be any type")
   T data;

   @Schema(description = "Message or messages providing more details about the response")
   Object message;

   @Schema(description = "HTTP status code of the response")
   Integer code;

}
