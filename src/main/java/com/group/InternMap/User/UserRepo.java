package com.group.InternMap.User;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
// optional means expect one or zero result

//        Optional<Users> findByEmail(String email);
}
