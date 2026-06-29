package com.example.demo;

import com.example.RestApi.app.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

//    @GetMapping("/user")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser() {
//        User user = new User(1,"Rahul", "Rahul@example.com");  
        return new User(1,"Rahul", "Rahul@example.com");    

    }
}
