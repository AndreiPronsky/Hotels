CREATE TABLE IF NOT EXISTS contacts
(
    id    BIGSERIAL PRIMARY KEY,
    phone VARCHAR(50)  NOT NULL,
    email VARCHAR(100) NOT NULL
);