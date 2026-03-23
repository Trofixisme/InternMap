package com.group.InternMap.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.info("Attempting to find user with the email: {}", email);

        // Fetch user from DB
        Users user;
        try {
            user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        } catch (UsernameNotFoundException e) {
            logger.warn("Failed to find user with the email {}. Email doesn't exist in the database", email);
            throw e;
        }

        // Converting result to Spring Security format
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // MUST be encoded
                .roles(user.getRole().name()) // STUDENT / RECRUITER
                .build();
    }
}
