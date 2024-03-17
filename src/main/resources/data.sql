
insert into personal_info (id,nam,surname,username, email, password, gender, authority, enabled) values (0,'Lýdia','Gábrišová','gabrisova',  'lydia@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','TEACHER', false);
insert into personal_info (id,nam,surname,username, email, password, gender, authority, enabled) values (1,'Miloš','Lukáčik','lukacik5', 'lukacik5@stud.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE', 'STUDENT', false);
insert into personal_info (id,nam,surname,username, email, password, gender, authority, enabled) values (2,'Anna','Lukáčiková','lukacikova6', 'lukacikova6@stud.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE', 'STUDENT', true);

insert into student (id,personal_number,points,absents,person_id) values (0,'559415',1,1,1);
insert into student (id,personal_number,points,absents,person_id) values (1,'587123',5,0,2);
insert into employee (id,personal_number, person_id) values (0,'646542', 0);

insert into chat(id, person_ida, person_idb) values (0,0,0);


insert into message(id,content, sender, chat_id, seen) values (0,'Ahoj Miloš', 'gabrisova', 0, true);
insert into message(id,content, sender, chat_id,  seen) values (1,'Ahoj Lydia', 'lukacik5', 0, true);

