package com.group.InternMap.Recruiter;

public class RecruiterAddedEvent {
    private final long recruiterId;
    private final String companyId;

    public RecruiterAddedEvent(long recruiterId, String companyId) {
        this.recruiterId = recruiterId;
        this.companyId = companyId;
    }

    public long getRecruiterId() { return recruiterId; }
    public String getCompanyId() { return companyId; }
}
