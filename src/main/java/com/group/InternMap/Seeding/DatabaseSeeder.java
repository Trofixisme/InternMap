package com.group.InternMap.Seeding;

import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyRepo;
import com.group.InternMap.Job.FullTime;
import com.group.InternMap.Job.Internship;
import com.group.InternMap.Job.JobRepo;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Recruiter.RecruiterRepo;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModule;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Skill.Skill;
import com.group.InternMap.User.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner initDatabase(RoadmapRepo roadmapRepo, JobRepo jobRepo, CompanyRepo companyRepo, RecruiterRepo recruiterRepo) {
        
        return _ -> {

            if (roadmapRepo.count() == 0) {
                // --- Frontend Roadmap ---
                Roadmap frontend = new Roadmap("Frontend Developer");

                RoadmapModule htmlCss = new RoadmapModule("HTML & CSS", "The building blocks of the web.");
                htmlCss.addSkills(
                        new Skill("HTML5 Semantic Elements", "Learn structural elements like <header>, <footer>, <article>.", List.of("https://developer.mozilla.org/en-US/docs/Glossary/Semantics#semantics_in_html")),
                        new Skill("CSS Flexbox", "Learn how to align items in a one-dimensional layout.", List.of("https://css-tricks.com/snippets/css/a-guide-to-flexbox/")),
                        new Skill("CSS Grid", "Learn two-dimensional layout for the web.", List.of("https://css-tricks.com/snippets/css/complete-guide-grid/"))
                );

                RoadmapModule jsBasics = new RoadmapModule("JavaScript Basics", "Learn the programming language of the web.");
                jsBasics.addSkills(
                        new Skill("ES6+ Syntax", "Arrow functions, destructuring, template literals.", List.of("https://javascript.info/es-modern")),
                        new Skill("DOM Manipulation", "Learn how to interact with HTML from JavaScript.", List.of("https://javascript.info/browser-environment"))
                );

                frontend.addModules(htmlCss, jsBasics);

                // --- Backend Roadmap ---
                Roadmap backend = new Roadmap("Backend Developer (Java)");

                RoadmapModule javaCore = new RoadmapModule("Java Fundamentals", "Core language features.");
                javaCore.addSkills(
                        new Skill("OOP Principles", "Encapsulation, Inheritance, Polymorphism, Abstraction.", List.of("https://docs.oracle.com/javase/tutorial/java/concepts/index.html")),
                        new Skill("Collections Framework", "List, Set, Map and their implementations.", List.of("https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html"))
                );

                RoadmapModule springBoot = new RoadmapModule("Spring Boot", "Build production-ready applications.");
                springBoot.addSkills(
                        new Skill("Dependency Injection", "IoC Container and Beans.", List.of("https://docs.spring.io/spring-framework/reference/core/beans/introduction.html")),
                        new Skill("Spring Data JPA", "Database access made easy.", List.of("https://spring.io/projects/spring-data-jpa"))
                );

                backend.addModules(javaCore, springBoot);

                // --- DevOps Roadmap ---
                Roadmap devops = new Roadmap("DevOps Engineer");

                RoadmapModule linux = new RoadmapModule("Linux Administration", "Master the command line.");
                linux.addSkills(
                        new Skill("Bash Scripting", "Automate tasks using shell scripts.", List.of("https://www.gnu.org/software/bash/manual/")),
                        new Skill("SSH & Networking", "Secure remote access and network basics.", List.of("https://www.ssh.com/academy/ssh/command"))
                );

                RoadmapModule docker = new RoadmapModule("Docker & Containers", "Containerization basics.");
                docker.addSkills(
                        new Skill("Docker Images", "Create and manage images.", List.of("https://docs.docker.com/get-started/")),
                        new Skill("Docker Compose", "Orchestrate multi-container applications.", List.of("https://docs.docker.com/compose/"))
                );

                devops.addModules(linux, docker);

                roadmapRepo.saveAll(List.of(frontend, backend, devops));
            }

            if (jobRepo.count() == 0) {

                // --- Companies ---
                Company techCorp = new Company();
                techCorp.setName("TechCorp Solutions");
                techCorp.setIndustry("Software Development");
                techCorp.setWebsiteURL("https://techcorp.example.com");

                Company innovateSoft = new Company();
                innovateSoft.setName("InnovateSoft");
                innovateSoft.setIndustry("Artificial Intelligence");
                innovateSoft.setWebsiteURL("https://innovatesoft.example.com");

                Company webPros = new Company();
                webPros.setName("WebPros Agency");
                webPros.setIndustry("Digital Marketing");
                webPros.setWebsiteURL("https://webpros.example.com");

                companyRepo.saveAll(List.of(techCorp, innovateSoft, webPros));

                // --- Recruiters ---
                Recruiter john = new Recruiter();
                john.setFName("John");
                john.setLName("Doe");
                john.setEmail("john.doe@techcorp.com");
                john.setPassword("password123");
                john.setRole(UserRole.RECRUITER);
                john.setTitle("Senior Technical Recruiter");
                john.addCompany(techCorp);

                Recruiter jane = new Recruiter();
                jane.setFName("Jane");
                jane.setLName("Smith");
                jane.setEmail("jane.smith@innovatesoft.com");
                jane.setPassword("securepass");
                jane.setRole(UserRole.RECRUITER);
                jane.setTitle("Talent Acquisition Manager");
                jane.addCompany(innovateSoft);

                recruiterRepo.saveAll(List.of(john, jane));

                // --- Job Postings ---
                FullTime softwareEngineer = new FullTime();
                softwareEngineer.setJobName("Senior Java Developer");
                softwareEngineer.setJobDescription("We are looking for an experienced Java Developer to join our backend team.");
                softwareEngineer.setJobRequirements("5+ years of Java experience, Spring Boot, MySQL.");
                softwareEngineer.setBenefits("Health insurance, 401k, remote work options.");
                softwareEngineer.setCompany(techCorp);
                softwareEngineer.setRecruiter(john);

                FullTime frontendEngineer = new FullTime();
                frontendEngineer.setJobName("Frontend React Developer");
                frontendEngineer.setJobDescription("Join our team to build beautiful and responsive user interfaces.");
                frontendEngineer.setJobRequirements("Strong knowledge of React, TypeScript, and Tailwind CSS.");
                frontendEngineer.setBenefits("Competitive salary, annual bonus, flexible hours.");
                frontendEngineer.setCompany(techCorp);
                frontendEngineer.setRecruiter(john);

                Internship aiIntern = new Internship();
                aiIntern.setJobName("AI Research Intern");
                aiIntern.setJobDescription("Help us develop the next generation of AI models.");
                aiIntern.setJobRequirements("Knowledge of Python and machine learning basics.");
                aiIntern.setCompany(innovateSoft);
                aiIntern.setRecruiter(jane);

                jobRepo.saveAll(List.of(softwareEngineer, frontendEngineer, aiIntern));
            }
        };
    }
}
