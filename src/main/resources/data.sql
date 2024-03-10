
insert into personalInfo (id,nam,surname,username, email, password,  authority, enabled) values (0,'Lydia','Gabrisova','gabrisova',  'lydia@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq','ADMIN', true);
insert into personalInfo (id,nam,surname,username, email, password, authority, enabled) values (1,'Miloš','Lukáčik','lukacik5', 'milos@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq','STUDENT', true);

insert into student (id,personal_number,points,absents,person_id) values (0,'559415',0,0,1);
insert into employee (id,personal_number, person_id) values (0,'646542', 0);