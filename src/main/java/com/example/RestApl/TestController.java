package com.example.RestApl;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@RestController
public class TestController {

    public static void main(String[] args) {
        SpringApplication.run(TestController.class, args);
    }

    @GetMapping("/demo")
    public String sayHello() {
        return "Hello Priyanshu! bina Bina security ke API chal gay";
    }
}
