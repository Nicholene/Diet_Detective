package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRep;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRep userRep;
    private final UserService userService;

    // Constructor for UserController
    public UserController(UserRep userRep, UserService userService) {
        this.userRep = userRep;
        this.userService = userService;
    }

    // Endpoint for adding a new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody AppUser appUser) {
        userService.validateUser(appUser); // Validate the user
        userRep.save(appUser); // Save the user to the repository
    }

    // Endpoint for getting a user by username
    @GetMapping("/{userName}")
    public AppUser getUser(@PathVariable String userName) {
        userService.validateDisplayName(userName); // Validate the username

        // Find the user in the repository
        Optional<AppUser> optionalExistingUser = userRep.findUserByUserName(userName);
        if (!optionalExistingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Throw an error if user not found
        }

        AppUser existingAppUser = optionalExistingUser.get();
        existingAppUser.setId(null); // Clear the id before returning

        return existingAppUser; // Return the found user
    }

    // Endpoint for updating user information
    @PutMapping("/{displayName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserInfo(@PathVariable String displayName, @RequestBody AppUser updatedAppUser) {
        userService.validateDisplayName(displayName); // Validate the display name

        // Find the user in the repository
        Optional<AppUser> optionalExistingUser = userRep.findUserByUserName(displayName);
        if (optionalExistingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Throw an error if user not found
        }

        AppUser existingAppUser = optionalExistingUser.get();

        // Copy the updated user info to the existing user
        userService.copyUserInfoFrom(updatedAppUser, existingAppUser);
        userRep.save(existingAppUser); // Save the updated user to the repository
    }
}
