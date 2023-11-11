package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserRep UserRep;


    public UserController(UserRep UserRep) {
        this.UserRep = UserRep;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        validateUser(user);


        UserRep.save(user);
    }


    @GetMapping("/{displayName}")
    public User getUser(@PathVariable String displayName) {
        validateDisplayName(displayName);


        Optional<User> optionalExistingUser = UserRep.findUserByDisplayName(displayName);
        if (!optionalExistingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        User existingUser = optionalExistingUser.get();
        existingUser.setId(null);


        return existingUser;
    }


    @PutMapping("/{displayName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserInfo(@PathVariable String displayName, @RequestBody User updatedUser) {
        validateDisplayName(displayName);


        Optional<User> optionalExistingUser = UserRep.findUserByDisplayName(displayName);
        if (optionalExistingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        User existingUser = optionalExistingUser.get();


        copyUserInfoFrom(updatedUser, existingUser);
        UserRep.save(existingUser);
    }


}
