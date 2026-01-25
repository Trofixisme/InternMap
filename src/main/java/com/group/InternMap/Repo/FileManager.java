package com.group.InternMap.Repo;

import com.group.InternMap.Model.Roadmap.Skill.Skill;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.UserRole;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class FileManager {

    // For writing individual objects (varargs style)
    protected static <T> void write(String fileName, T... objects) throws Exception {
        try (FileOutputStream stream = new FileOutputStream(fileName);
             ObjectOutputStream objectStream = new ObjectOutputStream(stream)) {

            if (objects != null && objects.length > 0) {
                objectStream.writeObject(new ArrayList<>(Arrays.asList(objects)));
            } else {
                objectStream.writeObject(new ArrayList<T>());
            }
        }
    }

    // For writing lists (used by BaseRepository.saveAll)
    protected static <T> void writeList(String fileName, List<T> items) throws Exception {
        try (FileOutputStream stream = new FileOutputStream(fileName);
             ObjectOutputStream objectStream = new ObjectOutputStream(stream)) {

            objectStream.writeObject(items != null ? new ArrayList<>(items) : new ArrayList<T>());
        }
    }

    protected static <T> ArrayList<T> read(Class<T> type, String fileName) throws Exception {
        try (FileInputStream stream = new FileInputStream(fileName);
             ObjectInputStream objectStream = new ObjectInputStream(stream)) {

            ArrayList<T> returningList = (ArrayList<T>) objectStream.readObject();
            return returningList != null ? returningList : new ArrayList<T>();
        }
    }

    private static final class RepositoryTest {

        static void main() throws Exception {
            String filePath = "data/test.txt";

            // Write 2 Skills
            write(filePath,
                    new Skill("Swift Student Challenge", "Empty", new ArrayList<>(List.of(new URL("https://developer.apple.com")))),
                    new Skill("Swift Student Challenge 2", "Empty", new ArrayList<>(List.of(new URL("https://www.apple.com/iphone-17/")))));

            System.out.println(read(Skill.class, filePath));  // Prints both skills

            // Overwrite with 3 Students
            write(filePath,
                    new Student("Ziad", "Ali", "ziad@example.com", "password123", UserRole.STUDENT, 2024, "Cairo University", "Computer Science", "FCAI"),
                    new Student("Alice", "Smith", "alice@example.com", "securePass", UserRole.STUDENT, 2025, "MIT", "Software Engineering", "Engineering"),
                    new Student("Bob", "Jones", "bob@example.com", "bobPass", UserRole.STUDENT, 2023, "Stanford", "Data Science", "Mathematics"),
                    new Student("hi","lol","lold@gmail.com","ofhdsj",UserRole.STUDENT,2023, "Stanford", "Data Science", "Mathematics")
            );

            System.out.println(read(Student.class, filePath));  // Prints 3 students
        }
    }
}