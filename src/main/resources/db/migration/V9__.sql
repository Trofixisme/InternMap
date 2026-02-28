CREATE SEQUENCE IF NOT EXISTS recruiter_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE recruiter
(
    id    BIGINT       NOT NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT pk_recruiter PRIMARY KEY (id)
);

CREATE TABLE recruiter_companies
(
    companies_id  BIGINT NOT NULL,
    recruiters_id BIGINT NOT NULL
);

ALTER TABLE recruiter
    ADD CONSTRAINT FK_RECRUITER_ON_ID FOREIGN KEY (id) REFERENCES "users" (id);

ALTER TABLE recruiter_companies
    ADD CONSTRAINT fk_reccom_on_company FOREIGN KEY (companies_id) REFERENCES company (id);

ALTER TABLE recruiter_companies
    ADD CONSTRAINT fk_reccom_on_recruiter FOREIGN KEY (recruiters_id) REFERENCES recruiter (id);