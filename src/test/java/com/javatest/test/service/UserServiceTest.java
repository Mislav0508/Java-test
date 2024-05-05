package com.javatest.test.service;

import com.javatest.test.model.User;
import com.javatest.test.repository.UserRepository;
import com.javatest.test.viewmodel.UserSaveViewModel;
import com.javatest.test.viewmodel.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

   @Mock
   private UserRepository userRepository;

   @InjectMocks
   private UserService userService;

   private User testUser;
   private UserSaveViewModel testUserViewModel;

   @BeforeEach
   void setUp() {
      testUser = new User();
      testUser.setUserId(1L);
      testUser.setFirstName("John");
      testUser.setLastName("Doe");
      testUser.setYearOfBirth(1985);

      testUserViewModel = new UserSaveViewModel();
      testUserViewModel.setUserId(1L);
      testUserViewModel.setFirstName("Jane");
      testUserViewModel.setLastName("Doe");
      testUserViewModel.setYearOfBirth(1990);
   }

   @Test
   void testCreateUser() {
      UserSaveViewModel viewModel = new UserSaveViewModel();
      viewModel.setFirstName("John");
      viewModel.setLastName("Doe");
      viewModel.setYearOfBirth(1991);
      User user = new User(viewModel);
      when(userRepository.save(any(User.class))).thenReturn(user);

      User created = userService.create(viewModel);

      assertNotNull(created);
      assertEquals("John", created.getFirstName());
      verify(userRepository).save(user);
   }

   @Test
   void testUpdateUser() throws Exception {
      User existingUser = testUser;

      User newUserDetails = new User();
      newUserDetails.setFirstName("Jane");
      newUserDetails.setLastName("Doe");
      newUserDetails.setYearOfBirth(1995);

      UserSaveViewModel viewModel = testUserViewModel;

      when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
      when(userRepository.save(any(User.class))).thenReturn(newUserDetails);

      User updated = userService.update(viewModel);

      assertEquals("Jane", updated.getFirstName());
      assertEquals(1995, updated.getYearOfBirth());
      verify(userRepository).save(any(User.class));
   }


   @Test
   void testDeleteUserNotFound() {
      when(userRepository.existsById(1L)).thenReturn(false);

      Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
         userService.delete(1L);
      });

      assertEquals("User with ID 1 not found", exception.getMessage());


      verify(userRepository, never()).deleteById(1L);
   }


   @Test
   void testUpdateUserNotFound() {
      when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

      User user = new User();
      user.setUserId(Long.valueOf(2));

      assertThrows(ResourceNotFoundException.class, () -> {
         userService.update(new UserSaveViewModel(user));
      });
   }
}
