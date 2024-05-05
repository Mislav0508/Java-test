package com.javatest.test.controller;

import com.javatest.test.model.User;
import com.javatest.test.service.UserService;
import com.javatest.test.service.errors.ErrorHandlerService;
import com.javatest.test.service.errors.LogWriterService;
import com.javatest.test.viewmodel.UserSaveViewModel;
import com.javatest.test.viewmodel.response.ApiResponse;
import com.javatest.test.viewmodel.response.Meta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

   @Autowired
   UserService userService;

   @Autowired
   LogWriterService logWriterService;

   @GetMapping("/{id}")
   public ApiResponse<UserSaveViewModel> getUser(@PathVariable("id") Long userId) {
      User user = userService.getUser(userId);
      return new ApiResponse<>(new Meta(true, System.currentTimeMillis()), new UserSaveViewModel(user), "User fetched successfully", HttpStatus.OK.value());
   }

   @PostMapping
   public ApiResponse<UserSaveViewModel> createUser(@Valid @RequestBody UserSaveViewModel viewModel, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return ErrorHandlerService.constructErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
      }

      User user = userService.create(viewModel);
      return new ApiResponse<>(new Meta(true, System.currentTimeMillis()), new UserSaveViewModel(user), "User successfully created.", HttpStatus.OK.value());
   }

   @PutMapping
   public ApiResponse<UserSaveViewModel> updateUser(@Valid @RequestBody UserSaveViewModel viewModel, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return ErrorHandlerService.constructErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
      }

      User user = userService.update(viewModel);
      return new ApiResponse<>(new Meta(true, System.currentTimeMillis()), new UserSaveViewModel(user), "User successfully updated.", HttpStatus.OK.value());
   }

   @DeleteMapping("/{id}")
   public ApiResponse<UserSaveViewModel> deleteUser(@PathVariable("id") Long userId) {
      userService.delete(userId);
      return new ApiResponse<>(new Meta(true, System.currentTimeMillis()), null, "User deleted successfully", HttpStatus.OK.value());
   }

}
