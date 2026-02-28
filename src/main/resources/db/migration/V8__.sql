ALTER TABLE job_posting_applications
DROP
CONSTRAINT fk_jobposapp_on_job_posting;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE "users"
(
    id             BIGINT       NOT NULL,
    role           SMALLINT,
    plain_password VARCHAR(255) NOT NULL,
    f_name         VARCHAR(255) NOT NULL,
    l_name         VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

DROP TABLE job_posting_applications CASCADE;