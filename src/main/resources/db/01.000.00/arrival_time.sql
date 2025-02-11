CREATE TABLE IF NOT EXISTS arrival_time
(
    id        BIGSERIAL PRIMARY KEY,
    check_in  TIME NOT NULL,
    check_out TIME NOT NULL
);