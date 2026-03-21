package com.group.InternMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.instrument.IllegalClassFormatException;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(InternMapApplication.class, args);
//        ShutDownSaver.registerShutdownHook();
    }
}
