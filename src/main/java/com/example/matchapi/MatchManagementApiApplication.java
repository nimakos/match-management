package com.example.matchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MatchManagementApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MatchManagementApiApplication.class, args);
    }
}