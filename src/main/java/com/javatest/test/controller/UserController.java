package com.javatest.test.controller;

import com.javatest.test.model.User;
import com.javatest.test.service.UserService;
import com.javatest.test.service.errors.ErrorHandlerService;
import com.javatest.test.service.errors.LogWriterService;
import com.javatest.test.viewmodel.UserSaveViewModel;
import com.javatest.test.viewmodel.response.ApiTestResponse;
import com.javatest.test.viewmodel.response.Meta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.javatest.test.viewmodel.exceptions.ErrorResponses.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

   @Autowired
   UserService userService;

   @Autowired
   LogWriterService logWriterService;

   @Operation(summary = "Get a user by ID", description = "Returns a single user",
           responses = {
                   @ApiResponse(responseCode = "200", description = "Successful operation",
                           content = @Content(schema = @Schema(implementation = UserSaveViewModel.class))),
                   @ApiResponse(responseCode = "404", description = "User not found",
                           content = @Content(examples = @ExampleObject(name = "NotFoundResponse",
                                   value = NOT_FOUND_RESPONSE))),
                   @ApiResponse(responseCode = "500", description = "Internal Server Error",
                           content = @Content(examples = @ExampleObject(name = "ServerErrorResponse",
                                   value = SERVER_ERROR_RESPONSE)))
           })
   @GetMapping("/{id}")
   public ApiTestResponse<UserSaveViewModel> getUser(@PathVariable("id") Long userId) {
      User user = userService.getUser(userId);
      return new ApiTestResponse<>(new Meta(true, System.currentTimeMillis()), new UserSaveViewModel(user), "User fetched successfully", HttpStatus.OK.value());
   }

   @Operation(summary = "Create a new user", description = "Creates a new user and returns that user",
           responses = {
                   @ApiResponse(responseCode = "201", description = "User created successfully",
                           content = @Content(schema = @Schema(implementation = UserSaveViewModel.class))),
                   @ApiResponse(responseCode = "400", description = "Invalid user data supplied",
                           content = @Content(examples = @ExampleObject(name = "BadRequestResponse",
                                   value = BAD_REQUEST_RESPONSE))),
                   @ApiResponse(responseCode = "500", description = "Internal Server Error",
                           content = @Content(examples = @ExampleObject(name = "ServerErrorResponse",
                                   value = SERVER_ERROR_RESPONSE)))
           })
   @PostMapping
   public ApiTestResponse<UserSaveViewModel> createUser(@Valid @RequestBody UserSaveViewModel viewModel, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return ErrorHandlerService.constructErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
      }

      User user = userService.create(viewModel);
      return new ApiTestResponse<>(new Meta(true, System.currentTimeMillis()), new UserSaveViewModel(user), "User successfully created.", HttpStatus.OK.value());
   }

   @Operation(summary = "Update a user", description = "Updates an existing user and returns the updated user",
           responses = {
                   @ApiResponse(responseCode = "200", description = "User updated successfully",
                           content = @Content(schema = @Schema(implementation = UserSaveViewModel.class))),
                   @ApiResponse(responseCode = "400", description = "Invalid user data supplied",
                           content = @Content(examples = @ExampleObject(name = "BadRequestResponse",
                                   value = BAD_REQUEST_RESPONSE))),
                   @ApiResponse(responseCode = "404", description = "User not found",
                           content = @Content(examples = @ExampleObject(name = "NotFoundResponse",
                                   value = NOT_FOUND_RESPONSE))),
                   @ApiResponse(responseCode = "500", description = "Internal Server Error",
                           content = @Content(examples = @ExampleObject(name = "ServerErrorResponse",
                                   value = SERVER_ERROR_RESPONSE)))
           })
   @PutMapping
   public ApiTestResponse<UserSaveViewModel> updateUser(@Valid @RequestBody UserSaveViewModel viewModel, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return ErrorHandlerService.constructErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
      }

      User user = userService.update(viewModel);
      return new ApiTestResponse<>(new Meta(true, System.currentTimeMillis()), new UserSaveViewModel(user), "User successfully updated.", HttpStatus.OK.value());
   }

   @Operation(summary = "Delete a user", description = "Deletes a user by ID",
           responses = {
                   @ApiResponse(responseCode = "200", description = "User deleted successfully"),
                   @ApiResponse(responseCode = "404", description = "User not found",
                           content = @Content(examples = @ExampleObject(name = "NotFoundResponse",
                                   value = NOT_FOUND_RESPONSE))),
                   @ApiResponse(responseCode = "500", description = "Internal Server Error",
                           content = @Content(examples = @ExampleObject(name = "ServerErrorResponse",
                                   value = SERVER_ERROR_RESPONSE)))
           })
   @DeleteMapping("/{id}")
   public ApiTestResponse<UserSaveViewModel> deleteUser(@PathVariable("id") Long userId) {
      userService.delete(userId);
      return new ApiTestResponse<>(new Meta(true, System.currentTimeMillis()), null, "User deleted successfully", HttpStatus.OK.value());
   }

}
