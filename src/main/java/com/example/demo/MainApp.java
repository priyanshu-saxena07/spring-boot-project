package com.example.demo;

import com.example.config.AppConfig;
import com.example.config.GreetingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {


        ApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);
       GreetingService greetingService
               = (GreetingService) context.getBean("MyBean");
       greetingService.sayHello();

//
//      UserService userService
//              = (UserService) context.getBean("UserServiceSMS");
//      userService.notifyUser("What's up");

//        UserService userServiceEmail
//                = (UserService) context.getBean("UserServiceEmail");
//        userServiceEmail.notifyUser("What's up");

//        System.out.println("Starting spring Application Context");
//        ApplicationContext context
//                = new AnnotationConfigApplicationContext(AppConfig.class);
//        System.out.println("Retrieving Lifecycle Bean");
//        LifecycleBean lifecycleBean = context.getBean(LifecycleBean.class);
//
//        System.out.println("Closing Spring Context");
    }
}
