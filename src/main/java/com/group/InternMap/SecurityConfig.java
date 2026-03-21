package com.group.InternMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
           .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/register", "/css/**").permitAll()
                    .requestMatchers("/student/**").hasRole("STUDENT")
                    .requestMatchers("/recruiter/**").hasRole("RECRUITER")
                    .requestMatchers("/recruiter/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
           )
           .formLogin(form -> form
                    .loginPage("/login")              // custom login page
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
           )
           .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
           );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}