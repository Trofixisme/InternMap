package com.group.InternMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.swing.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {

        http
           .authorizeHttpRequests(auth -> auth
//                   .requestMatchers("/student/**").hasRole("STUDENT")
                   .requestMatchers("/recruiter/**").hasRole("RECRUITER")
                   .requestMatchers("/admin/**").hasRole("ADMIN")
                   .anyRequest().permitAll()
           )
           .formLogin(form -> form
                   .loginPage("/login")              // custom login page
                   .defaultSuccessUrl("/", true)
                   .passwordParameter("password")
                   .usernameParameter("email")
                   .permitAll()
           )
           .logout(logout -> logout
                   .logoutUrl("/logout")
                   .logoutSuccessUrl("/")
                   .permitAll()
           ).rememberMe(rememberMe -> rememberMe
                        .rememberMeServices(rememberMeServices));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("CreditentialsKey", userDetailsService,
                encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        rememberMe.setAlwaysRemember(true);
        rememberMe.setCookieName("CreditentialsKey");
        rememberMe.setUseSecureCookie(true);
        return rememberMe;
    }
}