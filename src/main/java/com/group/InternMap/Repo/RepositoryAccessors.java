package com.group.InternMap.Repo;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.Roadmap.RoadmapModule;
import com.group.InternMap.Model.Roadmap.Skill.Skill;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Company;
import com.group.InternMap.Model.User.Users;
import com.group.InternMap.Services.FilePaths;


import java.util.ArrayList;

public final class RepositoryAccessors {
    public static final ArrayList<Users> ALL_USERS = (ArrayList<Users>) (new BaseRepository<>(Users.class, FilePaths.userPath)).findAll();
    public static final ArrayList<Company> allCompanies = (ArrayList<Company>) (new BaseRepository<>(Company.class, FilePaths.companyPath)).findAll();
    public static final ArrayList<Roadmap> allRoadmaps = (ArrayList<Roadmap>) (new BaseRepository<>(Roadmap.class, FilePaths.roadmapPath)).findAll();
    public static final ArrayList<JobPosting> allJobPostings = (ArrayList<JobPosting>) (new BaseRepository<>(JobPosting.class, FilePaths.jobPostingPath)).findAll();
    public static final ArrayList<Application> allApplications = (ArrayList<Application>) (new BaseRepository<>(Application.class, FilePaths.applicationPath)).findAll();
    public static final ArrayList<Skill> allskills = (ArrayList<Skill>) (new BaseRepository<>(Skill.class, FilePaths.skillPath)).findAll();
    public static final ArrayList<RoadmapModule> allmodules = (ArrayList<RoadmapModule>) (new BaseRepository<>(RoadmapModule.class, FilePaths.modulePath)).findAll();


    public static void saveAll() {
        try {
            new BaseRepository<>(Users.class, FilePaths.userPath).saveAll(ALL_USERS);
            new BaseRepository<>(Company.class, FilePaths.companyPath).saveAll(allCompanies);
            new BaseRepository<>(Roadmap.class, FilePaths.roadmapPath).saveAll(allRoadmaps);
            new BaseRepository<>(JobPosting.class, FilePaths.jobPostingPath).saveAll(allJobPostings);
            new BaseRepository<>(Application.class, FilePaths.applicationPath).saveAll(allApplications);
            new BaseRepository<>(Skill.class, FilePaths.skillPath).saveAll(allskills);

            System.out.println("\u001B[32mSaved all repositories successfully.\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mFailed to save one or more repositories:\u001B[0m");
            e.printStackTrace(); // better debugging output

        } finally {
            System.out.println("\u001B[34mInternMap Exited\u001B[0m");
        }
    }
    }

