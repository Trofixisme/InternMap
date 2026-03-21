package com.group.InternMap.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Fetch user from DB
        Users user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        // 2. Convert to Spring Security format
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // MUST be encoded
                .roles(user.getRole().name()) // STUDENT / RECRUITER
                .build();
    }
}
