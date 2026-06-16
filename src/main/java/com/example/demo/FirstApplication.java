package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.jwt", "com.example.security"})
public class FirstApplication {

    public static void main(String[] args) {
         SpringApplication.run(FirstApplication.class, args);
    }


}
