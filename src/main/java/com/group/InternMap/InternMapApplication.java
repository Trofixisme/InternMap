package com.group.InternMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class InternMapApplication {

    static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(InternMapApplication.class, args);
//        ShutDownSaver.registerShutdownHook();
    }
}
