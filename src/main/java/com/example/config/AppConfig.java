package com.example.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.config")
public class AppConfig {

//    @Bean(initMethod = "init", destroyMethod = "cleanup")
//    public LifecycleBean lifecycleBean(NotificationService notificationService) {
//        return new LifecycleBean(notificationService);
//    }

}
