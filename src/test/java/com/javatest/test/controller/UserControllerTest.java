package com.javatest.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatest.test.model.User;
import com.javatest.test.service.UserService;
import com.javatest.test.service.errors.LogWriterService;
import com.javatest.test.viewmodel.UserSaveViewModel;
import com.javatest.test.viewmodel.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private UserService userService;

   @MockBean
   private LogWriterService logWriterService;

   @BeforeEach
   void setUp() {
      Mockito.reset(userService, logWriterService);

      UserSaveViewModel testUserViewModel = new UserSaveViewModel();
      testUserViewModel.setUserId(1L);
      testUserViewModel.setFirstName("John");
      testUserViewModel.setLastName("Doe");
      testUserViewModel.setYearOfBirth(1990);

      User testUser = new User(testUserViewModel);
      when(userService.getUser(1L)).thenReturn(testUser);
      when(userService.create(any(UserSaveViewModel.class))).thenReturn(testUser);
      when(userService.update(any(UserSaveViewModel.class))).thenReturn(testUser);
   }

   @AfterEach
   void tearDown() {
      Mockito.verifyNoMoreInteractions(userService);
   }

   @Test
   void getUser() throws Exception {
      User mockUser = new User();
      mockUser.setFirstName("John");
      mockUser.setLastName("Doe");
      mockUser.setYearOfBirth(1985);

      when(userService.getUser(any(Long.class))).thenReturn(mockUser);

      mockMvc.perform(get("/api/users/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.data.firstName").value("John"))
              .andExpect(jsonPath("$.data.lastName").value("Doe"))
              .andExpect(jsonPath("$.data.yearOfBirth").value("1985"));

      verify(userService).getUser(1L);
   }

   @Test
   void getUserNotFound() throws Exception {
      Long userId = 1L;
      String errorMsg = "User not found with ID: " + userId;

      when(userService.getUser(userId)).thenThrow(new ResourceNotFoundException(errorMsg));

      mockMvc.perform(get("/api/users/" + userId))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$.meta.success").value(false))
              .andExpect(jsonPath("$.message").value(errorMsg))
              .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));

      verify(userService).getUser(userId);
   }


   @Test
   void createUser() throws Exception {
      UserSaveViewModel viewModel = new UserSaveViewModel();
      viewModel.setFirstName("John");
      viewModel.setLastName("Doe");
      viewModel.setYearOfBirth(1990);

      User user = new User(viewModel);
      when(userService.create(any(UserSaveViewModel.class))).thenReturn(user);

      mockMvc.perform(post("/api/users")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(new ObjectMapper().writeValueAsString(viewModel)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.data.firstName").value("John"))
               .andExpect(jsonPath("$.data.lastName").value("Doe"))
               .andExpect(jsonPath("$.data.yearOfBirth").value(1990));

      verify(userService).create(any(UserSaveViewModel.class));
   }

   @Test
   void createUserFailure() throws Exception {
      UserSaveViewModel viewModel = new UserSaveViewModel();
      viewModel.setFirstName("John");
      viewModel.setLastName("Doe");
      viewModel.setYearOfBirth(1990);

      when(userService.create(any(UserSaveViewModel.class))).thenThrow(new RuntimeException("Internal server error"));

      mockMvc.perform(post("/api/users")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(new ObjectMapper().writeValueAsString(viewModel)))
              .andExpect(status().isInternalServerError())
              .andExpect(jsonPath("$.meta.success").value(false))
              .andExpect(jsonPath("$.message").value("Internal server error"));

      verify(userService).create(any(UserSaveViewModel.class));
   }

   @Test
   void updateUser() throws Exception {
      UserSaveViewModel viewModel = new UserSaveViewModel();
      viewModel.setUserId(1L);
      viewModel.setFirstName("Jane");
      viewModel.setLastName("Dodo");
      viewModel.setYearOfBirth(1995);

      User user = new User(viewModel);
      when(userService.update(any(UserSaveViewModel.class))).thenReturn(user);

      mockMvc.perform(put("/api/users")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(new ObjectMapper().writeValueAsString(viewModel)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.data.firstName").value("Jane"))
              .andExpect(jsonPath("$.data.lastName").value("Dodo"))
              .andExpect(jsonPath("$.data.yearOfBirth").value(1995));


      verify(userService).update(any(UserSaveViewModel.class));
   }

   @Test
   void updateUserFailure() throws Exception {
      UserSaveViewModel viewModel = new UserSaveViewModel();
      viewModel.setUserId(1L);
      viewModel.setFirstName("Jane");
      viewModel.setLastName("Dodo");
      viewModel.setYearOfBirth(1995);

      String errorMsg = "User not found with ID: " + viewModel.getUserId();
      when(userService.update(any(UserSaveViewModel.class))).thenThrow(new ResourceNotFoundException(errorMsg));

      mockMvc.perform(put("/api/users")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(new ObjectMapper().writeValueAsString(viewModel)))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$.meta.success").value(false))
              .andExpect(jsonPath("$.message").value(errorMsg));

      verify(userService).update(any(UserSaveViewModel.class));
   }

   @Test
   void deleteUser() throws Exception {
      doNothing().when(userService).delete(1L);

      mockMvc.perform(delete("/api/users/1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.message").value("User deleted successfully"));

      verify(userService).delete(1L);
   }

   @Test
   void deleteUserFailure() throws Exception {
      Long userId = 1L;
      String errorMsg = "User not found with ID: " + userId;
      doThrow(new ResourceNotFoundException(errorMsg)).when(userService).delete(userId);

      mockMvc.perform(delete("/api/users/" + userId))
              .andExpect(status().isNotFound())
              .andExpect(jsonPath("$.meta.success").value(false))
              .andExpect(jsonPath("$.message").value(errorMsg));

      verify(userService).delete(userId);
   }

}