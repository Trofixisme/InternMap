package com.group.InternMap;

import com.group.InternMap.Deprecated.Repository.ShutDownSaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) throws IllegalClassFormatException {// REGISTER IT HERE
        ApplicationContext context = SpringApplication.run(InternMapApplication.class, args);
        ShutDownSaver.registerShutdownHook();
    }
}
