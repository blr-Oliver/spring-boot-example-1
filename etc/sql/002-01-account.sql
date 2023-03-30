CREATE TABLE account (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    password_hash varchar(500) NOT NULL,
    locked BOOLEAN NOT NULL DEFAULT FALSE,
    registered_at DATETIME NOT NULL DEFAULT NOW()
);

ALTER TABLE user ADD COLUMN account_id INT FIRST;
ALTER TABLE user_role ADD COLUMN account_id INT FIRST;
ALTER TABLE user_favorite_album ADD COLUMN account_id INT FIRST;
ALTER TABLE user_favorite_track ADD COLUMN account_id INT FIRST;

UPDATE user SET account_id = (@rownum:=1 + @rownum) WHERE 0 = (@rownum:=0) ORDER BY registered_at ASC;
UPDATE user_role r INNER JOIN user u ON r.user_login = u.login SET r.account_id = u.account_id;
UPDATE user_favorite_album f INNER JOIN user u ON f.user_login = u.login SET f.account_id = u.account_id;
UPDATE user_favorite_track f INNER JOIN user u ON f.user_login = u.login SET f.account_id = u.account_id;

INSERT INTO account(id, password_hash, registered_at) SELECT account_id, concat('{MD5}', md5(login)), registered_at FROM USER;
