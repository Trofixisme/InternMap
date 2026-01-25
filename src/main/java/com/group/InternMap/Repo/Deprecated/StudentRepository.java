package com.group.InternMap.Repo.Deprecated;

import com.group.InternMap.Model.User.Student;

import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;

import java.util.List;

@Deprecated
@SuppressWarnings("all")
public class StudentRepository extends BaseRepository {

    public StudentRepository() {
        super("data/Users.txt");
    }
//String fname, String lname, String email,  String plainPassword, UserRole role,
//                   //students attributes
//                   int graduatingYear, StudentMajor major, StudentDepartment department,

    User parseObject(String line) {

        String[] parts = line.split("\\|", -1);  // IMPORTANT: keep empty fields!

        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid line format (" + parts.length + " fields): " + line);
        }

//        return new Student(
//                parts[0],          // UUID
//                parts[1],          // First name
//                parts[2],          // Last name
//                parts[3],          // Email
//                parts[4],          // Password
//                UserRole.valueOf(parts[5]),//if enum
//                Integer.parseInt(parts[6]),//if integer
//                parts[7],
//                parts[8],
//                parts[9]
//        );
        return null;
    }


    private static class RepositoryTest {

        static void main(String[] args) {

            BaseRepository repo = new StudentRepository();

            List<Object> users = repo.findAll();
            users.forEach(System.out::println);
        }
    }

}
