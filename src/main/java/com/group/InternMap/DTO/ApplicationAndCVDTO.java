package com.group.InternMap.DTO;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Student.Student;

public class ApplicationAndCVDTO {
    private Application application;
    private Student student;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
