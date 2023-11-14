package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRep;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserRep userRep;

    UserService userService;

    public UserController(UserRep userRep) {
        this.userRep = userRep;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody AppUser appUser) {
        userService.validateUser(appUser);


        userRep.save(appUser);
    }


    @GetMapping("/{userName}")
    public AppUser getUser(@PathVariable String userName) {
        userService.validateDisplayName(userName);


        Optional<AppUser> optionalExistingUser = userRep.findUserByUserName(userName);
        if (!optionalExistingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        AppUser existingAppUser = optionalExistingUser.get();
        existingAppUser.setId(null);


        return existingAppUser;
    }


    @PutMapping("/{displayName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserInfo(@PathVariable String displayName, @RequestBody AppUser updatedAppUser) {
        userService.validateDisplayName(displayName);


        Optional<AppUser> optionalExistingUser = userRep.findUserByUserName(displayName);
        if (optionalExistingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        AppUser existingAppUser = optionalExistingUser.get();


        userService.copyUserInfoFrom(updatedAppUser, existingAppUser);
        userRep.save(existingAppUser);
    }


}
