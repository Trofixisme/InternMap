package com.group.InternMap.Student;

import com.group.InternMap.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Users findByEmail(String email);
    Users findById(long id);
}