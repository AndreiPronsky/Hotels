CREATE TABLE IF NOT EXISTS hotels
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR,
    brand        VARCHAR(255),
    address      BIGINT       NOT NULL,
    contacts     BIGINT       NOT NULL,
    arrival_time BIGINT       NOT NULL,
    amenities    BIGINT       NOT NULL
);

ALTER TABLE hotels
    ADD FOREIGN KEY (address) REFERENCES addresses (id);
ALTER TABLE hotels
    ADD FOREIGN KEY (contacts) REFERENCES contacts (id);
ALTER TABLE hotels
    ADD FOREIGN KEY (arrival_time) REFERENCES arrival_time (id);
ALTER TABLE hotels
    ADD FOREIGN KEY (amenities) REFERENCES amenities (id);