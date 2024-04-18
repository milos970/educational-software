insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (0,'Lýdia','Gábrišová','lydia.gabrisova', '56478', 'lydia.gabrisova@fri.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','TEACHER', true);
insert into employee (id, person_id) values (222, 0);

insert into system_settings(id,allowed_absents, number_of_students, employee_id, class_date, number_of_days)
    values (0,2,0,222,'12.05.2024 14:15',0);

insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (1,'Zuzana','Borčinová','zuzana.borcinova', '12345', 'zuzana.borcinova@fri.uniza.sk','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','EMPLOYEE', true);
insert into employee (id, person_id) values (223, 1);


insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (2,'Miloš','Lukáčik','lukacik5', '559415', 'numerika2024@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE','STUDENT', true);
insert into student (id, points, absents, person_id) values (0,0,0, 2);
insert into chat(participant_a,participant_b) values('lydia.gabrisova','lukacik5');

insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (3,'Jožko','Mrkvička','mrkvicka6', '123456', 'mrkvicka6@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE','STUDENT', true);
insert into student (id, points, absents, person_id) values (1,0,0, 3);
insert into chat(participant_a,participant_b) values('lydia.gabrisova','mrkvicka6');

insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (4,'Anna','Lukáčiková','lukacikova6', '654321', 'lukacikova6@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','STUDENT', true);
insert into student (id, points, absents, person_id) values (2,0,0, 4);
insert into chat(participant_a,participant_b) values('lydia.gabrisova','lukacikova6');

