package com.javatest.test.model;

import com.javatest.test.viewmodel.UserSaveViewModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "userId", nullable = false, unique = true)
   private Long userId;

   @NotBlank
   private String firstName;

   @NotBlank
   private String lastName;

   @NotNull
   private Integer yearOfBirth;

   public User(UserSaveViewModel viewModel) {
      this.userId = viewModel.getUserId();
      this.firstName = viewModel.getFirstName();
      this.lastName = viewModel.getLastName();
      this.yearOfBirth = viewModel.getYearOfBirth();
   }

}
