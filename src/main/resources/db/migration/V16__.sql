CREATE TABLE skill
(
    id          BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_skill PRIMARY KEY (id)
);