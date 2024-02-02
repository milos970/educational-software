

CREATE TABLE IF NOT EXISTS person
(
    id int,
    username varchar(255),
    password varchar(255),
    authority varchar(255)
);


insert into person (id, username, password, authority) values (0,'gabrisova','$2a$12$vpbJVejwREIGz4kiZvZRAeAOwzCXT8rg3OJ3m48VVgbZ3L9IQlbzG','ADMIN');
insert into person (id, username, password, authority) values (1,'lukacik','$2a$12$vpbJVejwREIGz4kiZvZRAeAOwzCXT8rg3OJ3m48VVgbZ3L9IQlbzG','STUDENT');