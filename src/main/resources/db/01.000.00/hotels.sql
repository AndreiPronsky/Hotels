CREATE TABLE IF NOT EXISTS hotels
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    brand        VARCHAR(255),
    address_id   BIGINT       NOT NULL,
    contacts_id  BIGINT       NOT NULL,
    arrival_time BIGINT       NOT NULL,
    amenities_id BIGINT       NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE,
    FOREIGN KEY (contacts_id) REFERENCES contacts(id) ON DELETE CASCADE,
    FOREIGN KEY (arrival_time) REFERENCES arrival_time(id) ON DELETE CASCADE,
    FOREIGN KEY (amenities_id) REFERENCES amenities(id) ON DELETE CASCADE
);