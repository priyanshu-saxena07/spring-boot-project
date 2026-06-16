package com.example.coupling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserServiceSMS")
public class UserService {
     public NotificationService notificationService;

     public UserService() {
     }

//     @Autowired
//    public UserService(@Qualifier("emailNotificationService") NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }

    @Autowired
    public UserService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void notifyUser(String message) {
        notificationService.send("Notification Hello");
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
