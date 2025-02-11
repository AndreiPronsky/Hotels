CREATE TABLE IF NOT EXISTS addresses
(
    id           BIGSERIAL PRIMARY KEY,
    house_number VARCHAR(255) NOT NULL,
    street       VARCHAR(255) NOT NULL,
    city         VARCHAR(255) NOT NULL,
    country      VARCHAR(255) NOT NULL,
    post_code    VARCHAR(255) NOT NULL
);