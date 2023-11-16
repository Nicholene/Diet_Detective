package com.example.demo.services;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRep;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class UserService {

    private final UserRep userRepository;

    // Constructor for dependency injection
    public UserService(UserRep userRepository) {
        this.userRepository = userRepository;
    }

    // Method to copy user info from updated user to existing user
    public void copyUserInfoFrom(AppUser updatedAppUser, AppUser existingAppUser) {
        if (ObjectUtils.isEmpty(updatedAppUser.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        updateIfNotNull(updatedAppUser::getCity, existingAppUser::setCity);
        updateIfNotNull(updatedAppUser::getState, existingAppUser::setState);
        updateIfNotNull(updatedAppUser::getZipCode, existingAppUser::setZipCode);
        updateIfNotNull(updatedAppUser::getPeanutAllergies, existingAppUser::setPeanutAllergies);
        updateIfNotNull(updatedAppUser::getDairyAllergies, existingAppUser::setDairyAllergies);
        updateIfNotNull(updatedAppUser::getEggAllergies, existingAppUser::setEggAllergies);
    }

    // Method to validate user
    public void validateUser(AppUser appUser) {
        validateDisplayName(appUser.getUserName());

        Optional<AppUser> existingUser = userRepository.findUserByUserName(appUser.getUserName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Method to validate display name
    public void validateDisplayName(String displayName) {
        if (ObjectUtils.isEmpty(displayName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    // Helper method to update value if not null
    private <T> void updateIfNotNull(Supplier<T> getter, Consumer<T> setter) {
        T value = getter.get();
        if (!ObjectUtils.isEmpty(value)) {
            setter.accept(value);
        }
    }
}
