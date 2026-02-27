ALTER TABLE job_posting
    ADD CONSTRAINT FK_JOBPOSTING_ON_RECRUITER FOREIGN KEY (recruiter_id) REFERENCES recruiter (id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT fk_jobposapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);