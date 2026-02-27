ALTER TABLE skill_resource_links
DROP
COLUMN resource_links;

ALTER TABLE skill_resource_links
    ADD resource_links OID;