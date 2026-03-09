package com.group.InternMap;

import com.group.InternMap.Deprecated.Repository.ShutDownSaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.instrument.IllegalClassFormatException;

@SpringBootApplication
public class InternMapApplication {

    public static void main(String[] args) throws IllegalClassFormatException {
        ApplicationContext context = SpringApplication.run(InternMapApplication.class, args);
        ShutDownSaver.registerShutdownHook();
//        Skill skill1 = new Skill("Java", "A high-level programming language used for building applications.");
//        RoadmapModule module1 = new RoadmapModule("Backend Development", skill1);
//        Roadmap roadmap = new Roadmap("Backend Developer",module1);
//        RoadmapRepo roadmapRepo = context.getBean(RoadmapRepo.class);
//        RoadmapModuleRepo roadmapModuleRepo = context.getBean(RoadmapModuleRepo.class);
//        SkillRepo skillRepo = context.getBean(SkillRepo.class);
//        skillRepo.save(skill1);
//        roadmapModuleRepo.save(module1);
//        roadmapRepo.save(roadmap);
    }
}
