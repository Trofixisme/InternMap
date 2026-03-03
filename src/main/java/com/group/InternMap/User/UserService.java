package com.group.InternMap.User;

import com.group.InternMap.FilePaths;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//crud operations
//create,read,update,delete
@Service
public class UserService implements FilePaths {

//    protected final BaseRepository<Users> repo = new BaseRepository<>(Users.class, userPath);
//    protected final BaseRepository<Roadmap> RoadmapRepo = new BaseRepository<>(Roadmap.class, roadmapPath);
    UserRepo userRepo;

    public void register(Users u) throws Exception {
        List<Users> users=userRepo.findAll();
        if (!users.contains(u)) {
            if(isEmailValid(u.getEmail())){
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
        List<Users> users = userRepo.findAll();
        for (Users u : users) {
            if (u.getEmail().strip().equalsIgnoreCase(email.strip())) {
                if (u.getPlainPassword().equals(password)) {
                    return u;
                } else if(!u.getPlainPassword().equals(password)) {
                    throw new Exception("Provided password is incorrect.");
                }
            }
        }
        throw new Exception("Couldn't find specified user.");
    }


    public Optional<Users> searchByEmail(String email) {
       return userRepo.findByEmail(email);
    }

    public Optional<Users> searchByID(long id) {
        return userRepo.findById(id);
    }
//      public List<Roadmap> viewRoadmaps() {
//        return RoadmapRepo.findAll();
//    }

}


