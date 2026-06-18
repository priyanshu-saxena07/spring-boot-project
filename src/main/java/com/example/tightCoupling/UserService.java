package com.example.tightCoupling;

public class UserService {
    NotificationService notificationService = new NotificationService();
    public void notifyUser(String message) {
        notificationService.send(message);
    }
}
