insert into personal_info (id,name,surname,username, personal_number, email, password, gender, authority, enabled)
values (0,'Ema','Mrkvičková','ema.mrkvickova', '56478', 'ema.mrkvičkova@fri.uniza.sk','$2a$12$yoHGHLBuo2e5xBjkbgBJReYa02vrFvP199WPDsmJPPu1HIcq3OL1e', 'FEMALE','TEACHER', true);

insert into employee (id, person_id) values (222, 0);

insert into system_settings(id,allowed_absents, employee_id, class_date, number_of_days, uploaded_file)
values (0,2,222,'28.09.2024 14:15',0,false);


insert into personal_info (id,name,surname,username, personal_number, email, password, gender, authority, enabled)
values (2,'Student','Student','student5', '123456', 'student@stud.uniza.sk','$2a$12$yoHGHLBuo2e5xBjkbgBJReYa02vrFvP199WPDsmJPPu1HIcq3OL1e', 'MALE','STUDENT', true);

insert into student (id, points, absents, person_id) values (0,0,0, 2);
insert into chat(participant_a,participant_b) values('ema.mrkvickova','student5');


insert into personal_info (id,name,surname,username, personal_number, email, password, gender, authority, enabled)
values (5,'Zuzana','Malá','zuzana.mala', '12345', 'zuzana.mala@fri.uniza.sk','$2a$12$yoHGHLBuo2e5xBjkbgBJReYa02vrFvP199WPDsmJPPu1HIcq3OL1e', 'FEMALE','EMPLOYEE', true);

insert into employee (id, person_id) values (225, 5);