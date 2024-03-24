
insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled) values (0,'Lýdia','Gábrišová','gabrisova', '56478', 'lydia@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','TEACHER', true);
insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled) values (1,'Miloš','Lukáčik','lukacik5', '559415', 'lukacik5@stud.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE', 'STUDENT', true);
insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled) values (2,'Anna','Lukáčiková','lukacikova6', '558123', 'lukacikova6@stud.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE', 'STUDENT', true);

insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled) values (3,'Eva','Jalova','eva', '123456', 'eva@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','STUDENT', true);
insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled) values (4,'Tomas','Tomi','tomas', '789101', 'tomas@stud.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE', 'STUDENT', true);
insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled) values (5,'Peter','Pet','peter', '121314', 'peter6@stud.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE', 'STUDENT', false);

insert into student (id,points,absents,person_id) values (0,0,0,1);
insert into student (id,points,absents,person_id) values (1,0,0,2);


insert into student (id,points,absents,person_id) values (2,0,0,3);
insert into student (id,points,absents,person_id) values (3,0,0,4);

insert into employee (id, person_id) values (0, 0);

insert into chat(participant_a, participant_b) values (0,1);


insert into message(id,content,sender_id,receiver_id,chat_participant_a,chat_participant_b,seen) values (0,'Ahoj',1,0,0,1,true);






