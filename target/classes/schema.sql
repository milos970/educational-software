drop table message;
drop table chat;
drop table person;




CREATE TABLE IF NOT EXISTS person
(
    person_id int,
    nam VARCHAR(255),
    surname VARCHAR(255),
    username VARCHAR(255),
    personal_number VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    authority VARCHAR(255),
    enabled BOOLEAN,
    points int,
    absencie int,
    chats JAVA_OBJECT
);


CREATE TABLE IF NOT EXISTS image
(
    id int,
    url VARCHAR(255)
);

