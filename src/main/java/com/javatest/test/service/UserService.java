package com.javatest.test.service;

import com.javatest.test.exceptions.ResourceNotFoundException;
import com.javatest.test.model.User;
import com.javatest.test.repository.UserRepository;
import com.javatest.test.viewmodel.UserSaveViewModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   @Autowired
   UserRepository userRepository;

   public User getUser(Long userId) {
      return userRepository.findById(userId)
                     .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));
   }

   @Transactional
   public User create(@Valid UserSaveViewModel viewModel) {
      User user = new User(viewModel);
      return userRepository.save(user);
   }



   @Transactional
   public User update(@Valid UserSaveViewModel viewModel) {
      User existingUser = userRepository.findById(viewModel.getUserId())
                                  .orElseThrow(() -> new ResourceNotFoundException("User with ID " + viewModel.getUserId() + " not found"));
      existingUser.setFirstName(viewModel.getFirstName());
      existingUser.setLastName(viewModel.getLastName());
      existingUser.setYearOfBirth(viewModel.getYearOfBirth());
      return userRepository.save(existingUser);
   }

   @Transactional
   public void delete(Long userId) {
      boolean exists = userRepository.existsById(userId);
      if (!exists) {
         throw new ResourceNotFoundException("User with ID " + userId + " not found");
      }
      userRepository.deleteById(userId);
   }

}
