ALTER TABLE media RENAME COLUMN media_type TO type;

ALTER TABLE media ALTER COLUMN title SET NOT NULL;
ALTER TABLE media ALTER COLUMN image_url SET NOT NULL;
ALTER TABLE media ALTER COLUMN description SET NOT NULL