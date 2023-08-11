CREATE TABLE media
(
    id                 UUID PRIMARY KEY,
    creation_timestamp TIMESTAMP,
    update_timestamp   TIMESTAMP,
    title              TEXT,
    image_url          TEXT,
    description        TEXT,
    media_type         TEXT NOT NULL,
    episodes           INTEGER
);
