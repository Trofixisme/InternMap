package com.group.InternMap.Student;

import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Application.Application;
import com.group.InternMap.FilePaths;
import com.group.InternMap.Job.JobRepo;
import com.group.InternMap.User.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allApplications;
//crud operations
//create,read,update,delete


@Service
public class StudentService extends UserService implements FilePaths {
    ApplicationRepo applicationRepo;
    JobRepo jobRepo;
    public void deleteApplication(Application application) throws Exception {
//            allApplications.remove(application);
//            jobPosting.deleteApplication(application);
        List<Application> applicationList = applicationRepo.findAll();
        if (applicationList.contains(application)) {
            applicationRepo.delete(application);
        }
        else {
            throw new Exception("There is no application with this specification");
        }

    }

}