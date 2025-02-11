CREATE TABLE IF NOT EXISTS amenities
(
    id BIGSERIAL PRIMARY KEY,
    free_parking BOOLEAN NOT NULL DEFAULT false,
    free_wifi BOOLEAN NOT NULL DEFAULT false,
    non_smoking_rooms BOOLEAN NOT NULL DEFAULT false,
    concierge BOOLEAN NOT NULL DEFAULT false,
    on_site_restaurant BOOLEAN NOT NULL DEFAULT false,
    fitness_center BOOLEAN NOT NULL DEFAULT false,
    pet_friendly_rooms BOOLEAN NOT NULL DEFAULT false,
    room_service BOOLEAN NOT NULL DEFAULT false,
    business_center BOOLEAN NOT NULL DEFAULT false,
    meeting_rooms BOOLEAN NOT NULL DEFAULT false
);