package com.group.InternMap.Student;

import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Application.Application;
import com.group.InternMap.FilePaths;
import com.group.InternMap.User.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allApplications;
//crud operations
//create,read,update,delete

@Service
public class StudentService extends UserService implements FilePaths {

    public void deleteApplication(Student student, JobPosting jobPosting, Application application) {
            allApplications.remove(application);
            jobPosting.deleteApplication(application);
    }

    public List<JobPosting> findJobpostingByname(String name) {
        return RepositoryAccessors.allJobPostings.stream().filter(job -> job.getJobName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public List<JobPosting> findJobpostingByID(String appId) {
        return RepositoryAccessors.allJobPostings.stream().filter(job -> job.getId().equals(appId)).collect(Collectors.toList());
    }
}