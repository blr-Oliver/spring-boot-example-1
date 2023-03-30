ALTER TABLE account ADD COLUMN email varchar(500) AFTER id;
UPDATE account a INNER JOIN user u ON a.id = u.account_id SET a.email = u.email;

ALTER TABLE account
    MODIFY COLUMN email varchar(500) NOT NULL,
    ADD CONSTRAINT uk_email UNIQUE KEY (email);

ALTER TABLE user DROP COLUMN email;