package com.example.RestApi.app.service;

import org.springframework.stereotype.Service;
import com.example.RestApi.app.model.User;
import java.util.*;


@Service
public class UserService {

    private final Map<Integer, User> userDb = new HashMap<>();

    public UserService() {
        userDb.put(1, new User(1, "Priyanshu", "priyanshu@gmail.com"));
    }

    public User createUser(User user) {
        userDb.put(user.getId(), user);
        return user;
    }

    public User getUserById(int id) {
        return userDb.get(id);
    }
}

