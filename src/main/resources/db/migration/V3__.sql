CREATE SEQUENCE IF NOT EXISTS company_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE company
(
    id            BIGINT       NOT NULL,
    industry      VARCHAR(255),
    name          VARCHAR(255) NOT NULL,
    websiteurl    VARCHAR(255),
    location_ofhq VARCHAR(255),
    CONSTRAINT pk_company PRIMARY KEY (id)
);