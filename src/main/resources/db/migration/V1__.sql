CREATE SEQUENCE IF NOT EXISTS full_time_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS job_posting_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE full_time
(
    id         BIGINT       NOT NULL,
    benefits   VARCHAR(255) NOT NULL,
    company_id BIGINT,
    CONSTRAINT pk_fulltime PRIMARY KEY (id)
);

CREATE TABLE full_time_applications
(
    full_time_id    BIGINT NOT NULL,
    applications_id BIGINT NOT NULL
);

CREATE TABLE job_posting
(
    id               BIGINT       NOT NULL,
    job_description  VARCHAR(255),
    date_posted      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    job_requirements VARCHAR(255),
    job_name         VARCHAR(255) NOT NULL,
    recruiter_id     BIGINT,
    CONSTRAINT pk_jobposting PRIMARY KEY (id)
);

CREATE TABLE job_posting_applications
(
    job_posting_id  BIGINT NOT NULL,
    applications_id BIGINT NOT NULL
);

ALTER TABLE full_time_applications
    ADD CONSTRAINT uc_full_time_applications_applications UNIQUE (applications_id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT uc_job_posting_applications_applications UNIQUE (applications_id);

ALTER TABLE full_time
    ADD CONSTRAINT FK_FULLTIME_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

ALTER TABLE full_time
    ADD CONSTRAINT FK_FULLTIME_ON_ID FOREIGN KEY (id) REFERENCES job_posting (id);

ALTER TABLE job_posting
    ADD CONSTRAINT FK_JOBPOSTING_ON_RECRUITER FOREIGN KEY (recruiter_id) REFERENCES recruiter (id);

ALTER TABLE full_time_applications
    ADD CONSTRAINT fk_fultimapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);

ALTER TABLE full_time_applications
    ADD CONSTRAINT fk_fultimapp_on_full_time FOREIGN KEY (full_time_id) REFERENCES full_time (id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT fk_jobposapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT fk_jobposapp_on_job_posting FOREIGN KEY (job_posting_id) REFERENCES job_posting (id);