package com.example.RestApl.app.controller;

import com.example.RestApl.app.model.User;
import com.example.RestApl.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService = new UserService();


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updated = userService.updateUser(user);
        if (updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return  ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<User> gitUsers() {
        return userService.getAllUsers();
    }

//     user/150, /user/25
    @GetMapping("/{userId}")
    public ResponseEntity<User> gitUser(
            @PathVariable(value = "userId", required = false) int id) {
        User user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User> gitUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ) {
        User user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }


    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
            @RequestParam(required = false, defaultValue = "lily")  String name,
            @RequestParam(required = false, defaultValue = "email")  String  email
    ) {

        return ResponseEntity.ok(userService.searchUsers(name, email));
    }

    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent) {
        return "User Agent: " + userAgent
        + " : " + id
        + " : " + name;
    }

    //  Exception handling method


}