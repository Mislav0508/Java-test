package com.javatest.test.service.errors;

import com.javatest.test.viewmodel.response.ApiResponse;
import com.javatest.test.viewmodel.response.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErrorHandlerService {

   public static ApiResponse constructErrorResponse(BindingResult bindingResult, HttpStatus httpStatus) {
      List<String> errorMessages = bindingResult.getAllErrors()
                     .stream()
                     .map(ObjectError -> ObjectError.getDefaultMessage())
                     .collect(Collectors.toList());
      return new ApiResponse<>(new Meta(false, System.currentTimeMillis()), null, errorMessages, httpStatus.value());
   }

}
