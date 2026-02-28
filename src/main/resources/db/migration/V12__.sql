CREATE SEQUENCE IF NOT EXISTS student_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE student
(
    id              BIGINT  NOT NULL,
    graduating_year INTEGER NOT NULL,
    uni_name        VARCHAR(255),
    student_major   VARCHAR(255),
    faculty         VARCHAR(255),
    CONSTRAINT pk_student PRIMARY KEY (id)
);

ALTER TABLE student
    ADD CONSTRAINT FK_STUDENT_ON_ID FOREIGN KEY (id) REFERENCES "users" (id);