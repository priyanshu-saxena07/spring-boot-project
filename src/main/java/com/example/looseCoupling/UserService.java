package com.example.looseCoupling;

import org.springframework.stereotype.Service;

@Service // @Component की जगह @Service लिखना बेहतर प्रैक्टिस है
public class UserService {

    // variable को private और final कर दिया ताकि यह सुरक्षित रहे
    private final NotificationService notificationService;

    // सिर्फ यही एक कंस्ट्रक्टर रहेगा। Spring इसी से loose coupling करेगा।
    // Modern Spring Boot में इसके ऊपर @Autowired लिखने की भी ज़रूरत नहीं होती।
    public UserService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void notifyUser(String message) {
        // यहाँ हमने hardcoded text हटाकर 'message' variable का यूज़ किया है
        notificationService.send(message);
    }
}
