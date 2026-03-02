package com.group.InternMap.User;

import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.FilePaths;
import com.group.InternMap.Deprecated.Repository.BaseRepository;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements FilePaths {

    protected final BaseRepository<Users> repo = new BaseRepository<>(Users.class, userPath);
    protected final BaseRepository<Roadmap> RoadmapRepo = new BaseRepository<>(Roadmap.class, roadmapPath);

    public void register(Users u) throws Exception {
        List<Users> users = RepositoryAccessors.ALL_USERS;

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

    public Users login(String email, String password) throws Exception {

        if (email == null || password == null) {
            throw new IllegalArgumentException("Neither the email nor the password are allowed to be empty.");
        }

        List<Users> users = RepositoryAccessors.ALL_USERS;

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


    public Users searchByEmail(String email) {
        try {
            List<Users> users = RepositoryAccessors.ALL_USERS;
            for (Users u : users) {
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
    public Users SearchbyID(String id) {
        try {
            List<Users> users = repo.findAll();
            for (Users u : users) {
                if (u.getId().toString().equals(id)) {
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


