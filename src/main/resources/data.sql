insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (0,'Lýdia','Gábrišová','gabrisova', '56478', 'lydia@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'FEMALE','TEACHER', true);

insert into employee (id, person_id) values (0, 0);

insert into system_settings(id,allowed_absents, number_of_students, employee_id, class_date, number_of_days, school_week)
    values (0,2,0,0,'12.05.2024 14:15', 500, 1);

insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (1,'Milan','Sedlacek','sedlacek', '56278', 'sedlacek@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE','EMPLOYEE', true);

insert into employee (id, person_id) values (1, 1);


insert into personal_info (id,nam,surname,username, personal_number, email, password, gender, authority, enabled)
values (2,'Milos','Lukacik','lukacik', '56878', 'milosnv570@gmail.com','$2a$12$svckNYyf7i6HsSPDraBGW.qCM3s2jml8Sb.ts0AfVzi2UCpaLeTOq', 'MALE','STUDENT', true);

insert into student (id, points, absents, person_id) values (2,0,0, 2);