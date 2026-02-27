CREATE SEQUENCE IF NOT EXISTS roadmap_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE roadmap
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_roadmap PRIMARY KEY (id)
);

CREATE TABLE roadmap_roadmap_modules
(
    roadmap_id         BIGINT NOT NULL,
    roadmap_modules_id BIGINT NOT NULL
);

ALTER TABLE roadmap_roadmap_modules
    ADD CONSTRAINT fk_roaroamod_on_roadmap FOREIGN KEY (roadmap_id) REFERENCES roadmap (id);

ALTER TABLE roadmap_roadmap_modules
    ADD CONSTRAINT fk_roaroamod_on_roadmap_module FOREIGN KEY (roadmap_modules_id) REFERENCES roadmap_module (id);