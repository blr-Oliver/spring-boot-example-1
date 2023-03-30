ALTER TABLE user
    MODIFY COLUMN public_name varchar(500) NULL,
    DROP INDEX public_name;
