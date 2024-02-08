

CREATE TABLE IF NOT EXISTS person
(
    id int,
    username varchar(255),
    password varchar(255),
    authority varchar(255)
);


insert into person (id, username, password, authority) values (0,'gabrisova','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq','ADMIN');
insert into person (id, username, password, authority) values (1,'lukacik','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq','STUDENT');
insert into person (id, username, password, authority) values (2,'marek','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq','STUDENT');