package com.group.InternMap.cv;

import com.group.InternMap.Student.Student;
import com.group.InternMap.Student.StudentRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/cv")
public class cvController {

    StudentRepo studentRepo;
    CVRepo cvRepo;
    UserService userService;

    @Autowired
    cvController(StudentRepo studentRepo,CVRepo cvRepo,UserService userService) {
        this.studentRepo = studentRepo;
        this.cvRepo = cvRepo;
        this.userService=userService;
    }

    @GetMapping
    public CV cv(Principal principal, Authentication authentication) throws HttpClientErrorException.Unauthorized {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            Student student = studentRepo.findByEmail(principal.getName());

            return student.getCv();
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of " + UserRole.STUDENT  +" to proceed");
        }
    }

    @PostMapping
    public ResponseEntity<Void> saveCV(@ModelAttribute("cv") CV cv,
                                       Principal principal,
                                       Authentication authentication) {
        // Better authority check
        if (authentication == null ||
                !authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {

            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,
                    "User must be of role STUDENT to proceed");
        }

        Student student = studentRepo.findByEmail(principal.getName());

        if (student == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Student not found");
        }

        CV cvToSave = student.getCv();  // existing CV or null

        if (cvToSave != null) {
            // Update existing CV
            cvToSave.setDescription(cv.getDescription());
            cvToSave.setPastExperiences(cv.getPastExperiences());
            cvToSave.setProjects(cv.getProjects());

            // TODO: Handle file updates when you implement file upload
            // cvToSave.setFileName(...);
            // cvToSave.setFileData(...);
            // etc.

            cvRepo.save(cvToSave);
        } else {
            // Create new CV
            cvToSave = cv;
            student.setCv(cvToSave);
            cvRepo.save(cvToSave);
        }

        studentRepo.save(student);

        return ResponseEntity.ok().build();  // Better than void + no response body
    }
    @GetMapping("/{email}")
    public CV viewCV(@PathVariable String email, Authentication authentication) {

        if (authentication != null && (authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.RECRUITER + "]") || authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]"))) {
            Optional<Users> retrievedStudent = userService.searchByEmail(email);
            if (retrievedStudent.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Student could not be found");
            }

            return ((Student) retrievedStudent.get()).getCv();

        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER or ADMIN to proceed");
        }
    }
}
