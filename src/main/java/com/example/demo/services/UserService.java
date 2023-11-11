package com.example.demo.services;

import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class UserService {
    private void copyUserInfoFrom(User updatedUser, User existingUser) {
        if (ObjectUtils.isEmpty(updatedUser.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        if (!ObjectUtils.isEmpty(updatedUser.getCity())) {
            existingUser.setCity(updatedUser.getCity());
        }


        if (!ObjectUtils.isEmpty(updatedUser.getState())) {
            existingUser.setState(updatedUser.getState());
        }


        if (!ObjectUtils.isEmpty(updatedUser.getZipCode())) {
            existingUser.setZipCode(updatedUser.getZipCode());
        }


        if (!ObjectUtils.isEmpty(updatedUser.getPeanutAllergies())) {
            existingUser.setPeanutAllergies(updatedUser.getPeanutAllergies());
        }


        if (!ObjectUtils.isEmpty(updatedUser.getDairyAllergies())) {
            existingUser.setDairyAllergies(updatedUser.getDairyAllergies());
        }


        if (!ObjectUtils.isEmpty(updatedUser.getEggAllergies())) {
            existingUser.setEggAllergies(updatedUser.getEggAllergies());
        }
    }


    private void validateUser(User user) {
        validateDisplayName(user.getUserName());


        Optional<User> existingUser = userRepository.findUserByDisplayName(user.getUserName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }


    private void validateDisplayName(String displayName) {
        if (ObjectUtils.isEmpty(displayName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
