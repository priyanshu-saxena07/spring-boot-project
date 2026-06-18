package com.example.tightCoupling;

public class AppMain {
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.notifyUser("Update your phone");
    }
}
