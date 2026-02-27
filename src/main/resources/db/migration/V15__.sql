ALTER TABLE roadmap_module_skills
DROP
CONSTRAINT fk_roamodski_on_roadmap_module;

CREATE SEQUENCE IF NOT EXISTS skill_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE skill
(
    id          BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_skill PRIMARY KEY (id)
);

DROP TABLE roadmap_module_skills CASCADE;