package com.example.config;

import org.springframework.stereotype.Component;

@Component("MyBean")
public class GreetingService {
    public void sayHello() {
        System.out.println("Hello from Spring");
    }

}
