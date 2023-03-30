ALTER TABLE user_favorite_album
    MODIFY COLUMN account_id INT NOT NULL,
    DROP PRIMARY KEY,
    DROP FOREIGN KEY user_favorite_album_ibfk_1,
    DROP COLUMN user_login,
    ADD CONSTRAINT PRIMARY KEY (account_id, album_id);

ALTER TABLE user_favorite_track
    MODIFY COLUMN account_id INT NOT NULL,
    DROP PRIMARY KEY,
    DROP FOREIGN KEY user_favorite_track_ibfk_1,
    DROP COLUMN user_login,
    ADD CONSTRAINT PRIMARY KEY (account_id, track_id);

ALTER TABLE user_role
    MODIFY COLUMN account_id INT NOT NULL,
    DROP PRIMARY KEY,
    DROP FOREIGN KEY user_role_ibfk_1,
    DROP COLUMN user_login,
    ADD CONSTRAINT PRIMARY KEY (account_id, role),
    RENAME TO account_role;

ALTER TABLE user
    CHANGE COLUMN account_id account_id INT NOT NULL,
    DROP PRIMARY KEY,
    ADD CONSTRAINT PRIMARY KEY (account_id),
    ADD CONSTRAINT fk_user__account_id FOREIGN KEY (account_id) references account(id) ON DELETE CASCADE ON UPDATE CASCADE,
    DROP COLUMN login,
    DROP COLUMN registered_at;

ALTER TABLE account_role
    ADD CONSTRAINT fk_account_role__account_id FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user_favorite_album
    ADD CONSTRAINT fk_user_favorite_album__account_id FOREIGN KEY (account_id) REFERENCES user (account_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user_favorite_track
    ADD CONSTRAINT fk_user_favorite_track__account_id FOREIGN KEY (account_id) REFERENCES user (account_id) ON DELETE CASCADE ON UPDATE CASCADE;
