CREATE SEQUENCE IF NOT EXISTS internship_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS job_posting_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE internship
(
    id        BIGINT  NOT NULL,
    duration  INTEGER NOT NULL,
    companyid BIGINT,
    CONSTRAINT pk_internship PRIMARY KEY (id)
);

CREATE TABLE internship_applications
(
    internship_id   BIGINT NOT NULL,
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

ALTER TABLE internship_applications
    ADD CONSTRAINT uc_internship_applications_applications UNIQUE (applications_id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT uc_job_posting_applications_applications UNIQUE (applications_id);

ALTER TABLE internship
    ADD CONSTRAINT FK_INTERNSHIP_ON_COMPANYID FOREIGN KEY (companyid) REFERENCES company (id);

ALTER TABLE internship
    ADD CONSTRAINT FK_INTERNSHIP_ON_ID FOREIGN KEY (id) REFERENCES job_posting (id);

ALTER TABLE job_posting
    ADD CONSTRAINT FK_JOBPOSTING_ON_RECRUITER FOREIGN KEY (recruiter_id) REFERENCES recruiter (id);

ALTER TABLE internship_applications
    ADD CONSTRAINT fk_intapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);

ALTER TABLE internship_applications
    ADD CONSTRAINT fk_intapp_on_internship FOREIGN KEY (internship_id) REFERENCES internship (id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT fk_jobposapp_on_application FOREIGN KEY (applications_id) REFERENCES application (id);

ALTER TABLE job_posting_applications
    ADD CONSTRAINT fk_jobposapp_on_job_posting FOREIGN KEY (job_posting_id) REFERENCES job_posting (id);