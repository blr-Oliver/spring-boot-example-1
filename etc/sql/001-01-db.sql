create SCHEMA `audiopedia` DEFAULT CHARACTER SET utf8;

create USER 'audiopedia-web' IDENTIFIED BY ''; -- password removed
grant select, insert, update, delete, execute ON audiopedia.* TO 'audiopedia-web';
