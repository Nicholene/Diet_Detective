package com.example.demo.services;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class UserService {

        UserRep userRepository;
    public void copyUserInfoFrom(AppUser updatedAppUser, AppUser existingAppUser) {
        if (ObjectUtils.isEmpty(updatedAppUser.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        if (!ObjectUtils.isEmpty(updatedAppUser.getCity())) {
            existingAppUser.setCity(updatedAppUser.getCity());
        }


        if (!ObjectUtils.isEmpty(updatedAppUser.getState())) {
            existingAppUser.setState(updatedAppUser.getState());
        }


        if (!ObjectUtils.isEmpty(updatedAppUser.getZipCode())) {
            existingAppUser.setZipCode(updatedAppUser.getZipCode());
        }


        if (!ObjectUtils.isEmpty(updatedAppUser.getPeanutAllergies())) {
            existingAppUser.setPeanutAllergies(updatedAppUser.getPeanutAllergies());
        }


        if (!ObjectUtils.isEmpty(updatedAppUser.getDairyAllergies())) {
            existingAppUser.setDairyAllergies(updatedAppUser.getDairyAllergies());
        }


        if (!ObjectUtils.isEmpty(updatedAppUser.getEggAllergies())) {
            existingAppUser.setEggAllergies(updatedAppUser.getEggAllergies());
        }
    }


    public void validateUser(AppUser appUser) {
        validateDisplayName(appUser.getUserName());


        Optional<AppUser> existingUser = userRepository.findUserByUserName(appUser.getUserName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }


    public void validateDisplayName(String displayName) {
        if (ObjectUtils.isEmpty(displayName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
