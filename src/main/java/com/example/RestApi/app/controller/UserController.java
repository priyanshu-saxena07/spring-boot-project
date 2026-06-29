package com.example.RestApi.app.controller;

import com.example.RestApi.app.model.User;
import com.example.RestApi.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. POST API - Working perfectly
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // 2. GET API - Restored back safely
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
       public ResponseEntity<String> deleteUser(@PathVariable("id") int userid) {
        boolean isDeleted = userService.deleteUser(userid);

         if (!isDeleted) {
             return  new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
         }

         return ResponseEntity.ok("User deleted successfully");
       }
}


