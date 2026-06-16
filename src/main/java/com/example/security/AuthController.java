package com.example.security;


import com.example.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 1. लॉगिन एंडपॉइंट - यह बिना टोकन के एक्सेस होगा
    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        // Spring Security के द्वारा यूज़रनेम और पासवर्ड को वेरिफाई करना
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // अगर क्रेडेंशियल्स सही हैं, तो टोकन जेनरेट करके वापस भेजें
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    // 2. सुरक्षित एंडपॉइंट - इसे एक्सेस करने के लिए JWT टोकन ज़रूरी होगा
    @GetMapping("/hello")
    public String sayHello() {
        return "सफलता! आपने JWT टोकन के ज़रिए इस सुरक्षित API को एक्सेस कर लिया है।";
    }
}
