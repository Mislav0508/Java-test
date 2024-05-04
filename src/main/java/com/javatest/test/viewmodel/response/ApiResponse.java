package com.javatest.test.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

   Meta meta;
   T data;
   Object message;
   Integer code;

}
