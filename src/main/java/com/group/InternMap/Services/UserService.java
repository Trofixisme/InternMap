package com.group.InternMap.Services;

import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.stereotype.Service;

import java.util.List;

//Ziad, Shimaa follow this structure for the rest of services
@Service
public class UserService implements FilePaths {
    protected final BaseRepository<User> repo = new BaseRepository<>(User.class, userPath);
    protected final BaseRepository<Roadmap> RoadmapRepo = new BaseRepository<>(Roadmap.class, roadmapPath);

    public void register(User u) throws Exception {
        List<User> users = RepositoryAccessors.allUsers;

        if (!users.contains(u)) {
            users.add(u);
        } else {
            throw new Exception("A user with these creditentials already exists.");
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

    public User login(String email, String password) throws Exception {

        if (email == null || password == null) {
            throw new IllegalArgumentException("Neither the email nor the password are allowed to be empty.");
        }

        List<User> users = RepositoryAccessors.allUsers;

        for (User u : users) {

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


    public User searchByEmail(String email) {
        try {
            List<User> users = RepositoryAccessors.allUsers;
            for (User u : users) {
                if (u.getEmail().equals(email)) {
                    return u;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @SuppressWarnings("unused")
    public User SearchbyID(String id) {
        try {
            List<User> users = repo.findAll();
            for (User u : users) {
                if (u.getUserID().toString().equals(id)) {
                    return u;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());


        }
        return null;
    }
    public List<Roadmap> viewRoadmaps() {
        return RoadmapRepo.findAll();
    }

}


