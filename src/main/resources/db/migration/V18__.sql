CREATE TABLE skill
(
    id          BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_skill PRIMARY KEY (id)
);

ALTER TABLE skill_resource_links
    ADD CONSTRAINT fk_skill_resourcelinks_on_skill FOREIGN KEY (skill_id) REFERENCES skill (id);