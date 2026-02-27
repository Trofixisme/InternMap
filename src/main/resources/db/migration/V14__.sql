ALTER TABLE roadmap_roadmap_modules
DROP
CONSTRAINT fk_roaroamod_on_roadmap;

CREATE SEQUENCE IF NOT EXISTS roadmap_module_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE roadmap_module
(
    id          BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_roadmapmodule PRIMARY KEY (id)
);

CREATE TABLE roadmap_module_skills
(
    roadmap_module_id BIGINT NOT NULL,
    skills_id         BIGINT NOT NULL
);

ALTER TABLE roadmap_module_skills
    ADD CONSTRAINT fk_roamodski_on_roadmap_module FOREIGN KEY (roadmap_module_id) REFERENCES roadmap_module (id);

ALTER TABLE roadmap_module_skills
    ADD CONSTRAINT fk_roamodski_on_skill FOREIGN KEY (skills_id) REFERENCES skill (id);

DROP TABLE roadmap_roadmap_modules CASCADE;