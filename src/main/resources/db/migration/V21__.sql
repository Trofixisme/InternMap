ALTER TABLE skill_resource_links
DROP
CONSTRAINT fk_skill_resourcelinks_on_skill;

CREATE SEQUENCE IF NOT EXISTS cv_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE cv
(
    cv_id            BIGINT NOT NULL,
    student_id       BIGINT,
    description      VARCHAR(255),
    past_experiences VARCHAR(255),
    projects         VARCHAR(255),
    CONSTRAINT pk_cv PRIMARY KEY (cv_id)
);

ALTER TABLE cv
    ADD CONSTRAINT FK_CV_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (id);

DROP TABLE skill_resource_links CASCADE;