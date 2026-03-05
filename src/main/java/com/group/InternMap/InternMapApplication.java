package com.group.InternMap;

import com.group.InternMap.Deprecated.Repository.ShutDownSaver;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModule;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Skill.Skill;
import com.group.InternMap.Student.Student;
import com.group.InternMap.User.UserRepo;
import com.group.InternMap.User.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) throws IllegalClassFormatException {// REGISTER IT HERE
//        ApplicationContext context = SpringApplication.run(InternMapApplication.class, args);
//        Student U=new Student("sandra","remon","sandra@gmail.com","1234",2025,"carleton","software engineering","engineering");
//        UserRepo user = context.getBean(UserRepo.class);
//         user.save(U);
//       List<String> l= List.of("python","c++","java");
//        Roadmap r=new Roadmap(new RoadmapModule("software"," "));
//        RoadmapRepo road= context.getBean(RoadmapRepo.class);
//        road.save(r);
        ShutDownSaver.registerShutdownHook();
    }
}
