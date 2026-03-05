package com.group.InternMap.Recruiter;

public class RecruiterAddedEvent {
    private final long recruiterId;
    private final long companyId;

    public RecruiterAddedEvent(long recruiterId, long companyId) {
        this.recruiterId = recruiterId;
        this.companyId = companyId;
    }

    public long getRecruiterId() { return recruiterId; }
    public long getCompanyId() { return companyId; }
}
