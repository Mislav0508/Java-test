package com.javatest.test.repository;

import com.javatest.test.model.User;
import com.javatest.test.viewmodel.UserSaveViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

   @Autowired
   private TestEntityManager entityManager;

   @MockBean
   private UserRepository userRepository;

   private User createMockUser (Long userId, String firstName, String lastName, Integer yearOfBirth) {
      UserSaveViewModel testUserViewModel = new UserSaveViewModel();
      testUserViewModel.setUserId(userId);
      testUserViewModel.setFirstName(firstName);
      testUserViewModel.setLastName(lastName);
      testUserViewModel.setYearOfBirth(yearOfBirth);

      return new User(testUserViewModel);
   }

   @Test
   public void testFindById() {
      User user = createMockUser(1L, "John", "Doe", 1991);
      when(userRepository.save(user)).thenReturn(user);

      when(userRepository.findById(1L)).thenReturn(Optional.of(user));

      Optional<User> found = userRepository.findById(user.getUserId());

      assertTrue(found.isPresent(), "User should be found");
      assertEquals(found.get().getUserId(), user.getUserId(), "User IDs should match");
   }



   @Test
   public void testSaveUser() {
      User mockUser = createMockUser(1L, "John", "Doe", 1991);
      when(userRepository.save(mockUser)).thenReturn(mockUser);

      assertThat(mockUser.getFirstName()).isEqualTo("John");
   }

   @Test
   public void testUpdateUser() {
      User mockUser = createMockUser(1L, "John", "Doe", 1991);

      when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

      mockUser.setFirstName("updatedFirstname");
      when(userRepository.save(mockUser)).thenReturn(mockUser);

      User updatedUser = userRepository.save(mockUser);

      assertThat(updatedUser.getFirstName()).isEqualTo("updatedFirstname");
   }

   @Test
   public void testDeleteUser() {
      User user = createMockUser(1L, "John", "Doe", 1991);
      userRepository.delete(user);

      User deletedUser = entityManager.find(User.class, user.getUserId());

      assertNull(deletedUser, "The user should have been deleted from the database");
   }

}
