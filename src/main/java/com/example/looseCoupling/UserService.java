package com.example.looseCoupling;

import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final NotificationService notificationService;

    public UserService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void notifyUser(String message) {
        notificationService.send(message);
    }
}
