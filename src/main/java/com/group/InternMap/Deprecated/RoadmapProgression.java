//package com.group.InternMap.Deprecated;
//
//import com.group.InternMap.Roadmap.Roadmap;
//import com.group.InternMap.Roadmap.Skill.UserSkillStatus;
//import com.group.InternMap.Roadmap.Status;
//import com.group.InternMap.Model.User.Student;
////import com.group.educate.Model.User.Student.StudentMajor;
//import java.io.Serializable;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.UUID;
//
//@Deprecated
//public class RoadmapProgression implements Serializable {
//
//    private UUID uuid;
//    private double percentage;
//    private Date lastModified = Date.from(Instant.now());
//    private Student associatedStudent;
//    private Roadmap roadmap;
//    private ArrayList<UserSkillStatus> userSkillStatuses = new ArrayList<>();
//    private double currentCompletionPercentage;
//    private int numberOfCompleteModules;
//    private int numberOfIncompleteModules;
//
//    public RoadmapProgression(Roadmap roadmap, Student Associatedstudent) {
//        uuid = UUID.randomUUID();
//        this.roadmap = roadmap;
//        this.associatedStudent = Associatedstudent;
//    }
//
//    public RoadmapProgression(String uuid, Roadmap roadmap, Student Associatedstudent) {
//        this.uuid = UUID.fromString(uuid);
//        this.roadmap = roadmap;
//        this.associatedStudent = Associatedstudent;
//    }
//
//    public void addUserSkillStatus(UserSkillStatus userSkillStatus) {
//        userSkillStatus.setDelegate(this);
//        userSkillStatuses.add(userSkillStatus);
//        updateCompletionPercentage();
//        updateLastModified();
//    }
//
//    public void updateCompletionPercentage() {
//        if (userSkillStatuses.isEmpty()) {
//            percentage = 0;
//            numberOfCompleteModules = 0;
//            numberOfIncompleteModules = 0;
//            return;
//        }
//
//        int CompleteCount = 0;
//        for (UserSkillStatus status : userSkillStatuses) {
//            if (status.getStatus() == Status.DONE || status.getStatus() == Status.SKIPPED) {
//                CompleteCount++;
//            }
//        }
//
//        numberOfCompleteModules = CompleteCount;
//        numberOfIncompleteModules = userSkillStatuses.size() - CompleteCount;
//        percentage = ((double) CompleteCount / userSkillStatuses.size()) * 100;
//        currentCompletionPercentage = percentage;
//    }
//
//    private void updateLastModified() {
//        lastModified = Date.from(Instant.now());
//    }
//
//    public String getRoadmapProgressionID() {
//        return uuid.toString();
//    }
//
//    public double getPercentage() {
//        return percentage;
//    }
//
//    public int getNumberOfCompleteModules() {
//        return numberOfCompleteModules;
//    }
//
//    public int getNumberOfIncompleteModules() {
//        return numberOfIncompleteModules;
//    }
//
//    public Date getLastModified() {
//        return lastModified;
//    }
//
//}