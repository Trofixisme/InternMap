package com.group.InternMap.User;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<Users, Long> {
    // optional means expect zero or more results
    Optional<Users> findByEmail(String email);
}
