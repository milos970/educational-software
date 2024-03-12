
insert into personal_info (id,nam,surname,username, email, password, gender, authority, enabled) values (0,'Lýdia','Gábrišová','gabrisova',  'lydia@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','TEACHER', true);
insert into personal_info (id,nam,surname,username, email, password, gender, authority, enabled) values (1,'Miloš','Lukáčik','lukacik5', 'milos@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE', 'STUDENT', true);

insert into student (id,personal_number,points,absents,person_id) values (0,'559415',0,0,1);
insert into employee (id,personal_number, person_id) values (0,'646542', 0);