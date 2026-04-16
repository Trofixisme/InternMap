package com.group.InternMap.User;

import com.group.InternMap.FilePaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

//CRUD operations
//Create, Read, Update, Delete
@Service
public class UserService implements FilePaths {

    AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;
    private UserRepo userRepo;

    public UserService() {}

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(Users u, HttpServletRequest request) throws ServletException {
        String plainPassword = u.getPassword();
        register(u);
        request.logout();
        request.login(u.getEmail(), plainPassword);
    }

    public void register(Users u) {
        try {
            if (isEmailValid(u.getEmail())) {
                u.setPassword(passwordEncoder.encode(u.getPassword()));
                userRepo.save(u);
            }
        } catch (DataIntegrityViolationException e) {
            if (!e.getMessage().contains("could")) {
                throw new DataIntegrityViolationException("User with this email already exists.");
            } else {
                throw new DataIntegrityViolationException("Required Data Missing");
            }

        }
    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        boolean startsWithAt = false;
        for (int i = 0; i < email.length(); i++) {
            if (!startsWithAt && email.charAt(i) == '.' && !email.contains(".") && (email.startsWith("@") && email.startsWith("."))) {
                throw new IllegalArgumentException("Email must in the following format: name@example.com");
            } else {
                startsWithAt = true;
            }
            if (((email.charAt(i) == '@' && (Character.isAlphabetic(email.charAt(i + 1)) || Character.isDigit(email.charAt(i + 1)))))) {
                if (Character.isSpaceChar(email.charAt(i)) || Character.isEmoji(email.charAt(i))) {
                    throw new IllegalArgumentException("Email cannot contain spaces or emojis.");
                }
            }
        }
        return true;
    }

    public Users login(String email, String password) throws Exception {
    if (email == null || password == null) {
        throw new IllegalArgumentException("Neither the email nor the password are allowed to be empty.");
    }

    Optional<Users> optionalUser = userRepo.findByEmail(email);
    if (optionalUser.isEmpty()) {
        throw new Exception("No user found with that email.");
    }

    Users user = optionalUser.get();
    if (user.getPassword().equals(password)) {
        return user;
    } else {
        throw new Exception("Provided password is incorrect.");
    }
}

    public Optional<Users> searchByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<Users> searchByID(long id) {
        return userRepo.findById(id);
    }

}
