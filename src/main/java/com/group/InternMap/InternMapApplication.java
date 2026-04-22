package com.group.InternMap;

import com.group.InternMap.Application.ApplicationService;
import com.group.InternMap.Seeding.DatabaseSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@SpringBootApplication
public class InternMapApplication {

    public static ApplicationContext context;

    static void main(String[] args) {
        context = SpringApplication.run(InternMapApplication.class, args);
//        ShutDownSaver.registerShutdownHook();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/REST/**").allowedOrigins("http://localhost:5173");
                registry.addMapping("/api/**").allowedOrigins("http://localhost:5173");
            }
        };
    }
}
