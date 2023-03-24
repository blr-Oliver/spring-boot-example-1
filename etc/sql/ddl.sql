CREATE TABLE track (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    duration TIME NOT NULL,
    genre VARCHAR(100)
);

CREATE TABLE author (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(500) NOT NULL,
    short_name VARCHAR(100)
);

CREATE TABLE album (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    published DATE
);

CREATE TABLE user (
    login VARCHAR(100) NOT NULL PRIMARY KEY,
    email VARCHAR(500) NOT NULL UNIQUE,
    public_name VARCHAR(500) NOT NULL UNIQUE,
    registered_at DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE album_track (
    album_id INT NOT NULL,
    track_id INT NOT NULL,
    idx INT NOT NULL,
    PRIMARY KEY (album_id, track_id),
    UNIQUE KEY (album_id, idx),
    FOREIGN KEY (album_id)
        REFERENCES album (id)
        ON DELETE RESTRICT,
    FOREIGN KEY (track_id)
        REFERENCES track (id)
        ON DELETE CASCADE
);

CREATE TABLE track_author (
    track_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (track_id, author_id),
    FOREIGN KEY (track_id)
        REFERENCES track (id)
        ON DELETE CASCADE,
    FOREIGN KEY (author_id)
        REFERENCES author (id)
        ON DELETE RESTRICT
);

CREATE TABLE user_role (
    user_login VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_login, role),
    FOREIGN KEY (user_login)
        REFERENCES user (login)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE user_favorite_album (
    user_login VARCHAR(100) NOT NULL,
    album_id INT NOT NULL,
    PRIMARY KEY (user_login, album_id),
    FOREIGN KEY (user_login)
        REFERENCES user (login)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (album_id)
        REFERENCES album (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE user_favorite_track (
    user_login VARCHAR(100) NOT NULL,
    track_id INT NOT NULL,
    PRIMARY KEY (user_login, track_id),
    FOREIGN KEY (user_login)
        REFERENCES user (login)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (track_id)
        REFERENCES track (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);