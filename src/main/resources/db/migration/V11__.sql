ALTER TABLE job_posting_applications
DROP
CONSTRAINT fk_jobposapp_on_job_posting;

CREATE SEQUENCE IF NOT EXISTS application_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE application
(
    id               BIGINT       NOT NULL,
    f_name           VARCHAR(255) NOT NULL,
    l_name           VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL,
    phone_number     VARCHAR(255) NOT NULL,
    application_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    student_id       BIGINT,
    CONSTRAINT pk_application PRIMARY KEY (id)
);

ALTER TABLE application
    ADD CONSTRAINT FK_APPLICATION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (id);

DROP TABLE job_posting_applications CASCADE;