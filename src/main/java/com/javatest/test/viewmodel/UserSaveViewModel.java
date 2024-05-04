package com.javatest.test.viewmodel;

import com.javatest.test.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveViewModel {

   private Long userId;

   @NotBlank(message = "First name must not be blank")
   @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters long")
   private String firstName;

   @NotBlank(message = "Last name must not be blank")
   @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters long")
   private String lastName;

   @NotNull(message = "Year of birth must not be null")
   private Integer yearOfBirth;

   public UserSaveViewModel(User user) {
      this.userId = user.getUserId();
      this.firstName = user.getFirstName();
      this.lastName = user.getLastName();
      this.yearOfBirth = user.getYearOfBirth();
   }
}
