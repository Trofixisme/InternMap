ALTER TABLE job_posting
    ADD recruiter_userid UUID;

ALTER TABLE job_posting
    ADD CONSTRAINT FK_JOBPOSTING_ON_REUSREID FOREIGN KEY (recruiter_userid, recruiter_id) REFERENCES recruiter (userid, id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT fk_jobposapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);