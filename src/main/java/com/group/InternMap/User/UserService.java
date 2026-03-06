package com.group.InternMap.User;

import com.group.InternMap.FilePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//crud operations
//create,read,update,delete
@Service
public class UserService implements FilePaths {

    UserRepo userRepo;

    public UserService() {}

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void register(Users u) throws Exception {
        List<Users> users = userRepo.findAll(); // findAll() is built in JPARepository
        if (!users.contains(u)) {
            if(isEmailValid(u.getEmail())) {
                //built into JPARepository
                userRepo.save(u);
            }
        } else {
            throw new Exception("A user with these credentials already exists.");
        }
    }

    public static boolean isEmailValid(String email) {
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

    Users user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    if (user.getPlainPassword().equals(password)) {
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


