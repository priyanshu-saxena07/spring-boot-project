package com.example.demo;


import com.example.looseCoupling.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.example.looseCoupling",})
public class FirstApplication {

    public static void main(String[] args) {
         SpringApplication.run(FirstApplication.class, args);
    }

    @Bean
    @SuppressWarnings("unused")
      CommandLineRunner run(UserService userService) {
        return args -> userService.notifyUser("Hello");

    }

}
