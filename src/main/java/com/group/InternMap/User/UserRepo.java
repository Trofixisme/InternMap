package com.group.InternMap.User;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    // optional means expect zero or more results
    Optional<Users> findByEmail(String email);
}
