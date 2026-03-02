package com.group.InternMap.Deprecated;

import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import com.group.InternMap.FilePaths;
import com.group.InternMap.User.UserService;
import com.group.InternMap.Student.Student;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Deprecated
public class AdminService extends UserService implements FilePaths {

    public List<Users> viewAllUsers() {
        return RepositoryAccessors.ALL_USERS;
    }

    public List<Student> findAllStudents() {
        return RepositoryAccessors.ALL_USERS.stream()
//                .filter(u -> u.getRole() == UserRole.STUDENT)
                .map(u -> (Student) u)
                .toList();
    }

    public List<Recruiter> findAllRecruiters() {
        return RepositoryAccessors.ALL_USERS.stream()
//                .filter(u -> u.getRole() == UserRole.RECRUITER)
                .map(u -> (Recruiter) u)
                .toList();
    }
    //Deleting users

    //Doesn't matter which one since email is unique
    public void deleteUser(String email) {

        List<Users> users = RepositoryAccessors.ALL_USERS;
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
