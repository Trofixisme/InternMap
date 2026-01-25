package com.group.InternMap.Repo.Deprecated;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;
import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Services.FilePaths;
import com.group.InternMap.Services.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Deprecated
public class AdminService extends UserService implements FilePaths {

    public List<User> viewAllUsers() {
        return RepositoryAccessors.allUsers;
    }

    public List<Student> findAllStudents() {
        return RepositoryAccessors.allUsers.stream()
//                .filter(u -> u.getRole() == UserRole.STUDENT)
                .map(u -> (Student) u)
                .toList();
    }

    public List<Recruiter> findAllRecruiters() {
        return RepositoryAccessors.allUsers.stream()
//                .filter(u -> u.getRole() == UserRole.RECRUITER)
                .map(u -> (Recruiter) u)
                .toList();
    }
    //Deleting users

    //Doesn't matter which one since email is unique
    public void deleteUser(String email) {

        List<User> users = RepositoryAccessors.allUsers;
        users.removeIf(u -> u.getEmail().equals(email));
    }

    public void addRoadmap(Roadmap roadmap) {
        List<Roadmap> roadmaps= RepositoryAccessors.allRoadmaps;
        roadmaps.add(roadmap);
    }

    public void removeRoadmap(Roadmap roadmap) {
        RepositoryAccessors.allRoadmaps.remove(roadmap);
    }

    //View all job posting
    public List<JobPosting> viewAllJobPosting() {
        return RepositoryAccessors.allJobPostings;
    }

    public void removeJobPosting(JobPosting jobPosting) {
        RepositoryAccessors.allJobPostings.remove(jobPosting);
    }

}
