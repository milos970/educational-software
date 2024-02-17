CREATE TABLE IF NOT EXISTS person
(
    id      int PRIMARY KEY,
    username VARCHAR(50) ,
    password VARCHAR(100),
    authority VARCHAR(20)
);