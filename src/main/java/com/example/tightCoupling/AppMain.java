package com.example.tightCoupling;

public class AppMain {
    public static void main(String[] args) {
        // tight
        UserService userService = new UserService();
        userService.notifyUser("Update your phone");

    }
}
