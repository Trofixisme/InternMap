package com.group.InternMap.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<Users, Long> {
        Optional<Users> findByEmail(String email);

//        Optional<Users> findByEmail(String email);
}
