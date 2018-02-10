INSERT INTO users(user_name, first_name, last_name, email, encrypted_password, salt, user_state_id)
	VALUES('gheineman', 'George', 'Heineman', 'heineman@wpi.edu', '12345', '123', 1);
INSERT INTO users(user_name, first_name, last_name, email, encrypted_password, salt, user_state_id)
	VALUES('mciaraldi', 'Mike', 'Ciaraldi', 'ciaraldi@wpi.edu', '54321', '321', 2);
INSERT INTO users(user_name, first_name, last_name, email, encrypted_password, salt, user_state_id)
	VALUES('crich', 'Charles', 'Rich', 'rich@wpi.edu', '13579', '135', 2);
INSERT INTO users(user_name, first_name, last_name, email, encrypted_password, salt, user_state_id)
	VALUES('wwong', 'Wilson', 'Wong', 'wwong2@wpi.edu', '24680', '246', 1);
INSERT INTO users(user_name, first_name, last_name, email, encrypted_password, salt, user_state_id)
	VALUES('kpray', 'Keith', 'Pray', 'kap@wpi.edu', '59472', '876', 1);

INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'A', 'MTRF', 900, 950);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'B', 'MTRF', 1500, 1550);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'B', 'MTRF', 1000, 1050);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'C', 'MTRF', 1200, 1250);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'C', 'MTRF', 1400, 1450);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'D', 'MTRF', 800, 850);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'A', 'MTRF', 1400, 1450);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'A', 'MTRF', 1000, 1050);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'B', 'MTRF', 1100, 1150);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'B', 'MTRF', 1500, 1550);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'C', 'MTRF', 1300, 1350);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'C', 'MTRF', 900, 950);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'D', 'MTRF', 1000, 1050);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'D', 'MTRF', 1400, 1450);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'A', 'MTRF', 1000, 1050);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'A', 'MTRF', 1100, 1150);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'B', 'MTRF', 800, 850);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'B', 'MTRF', 900, 950);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'C', 'MTRF', 1200, 1250);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES (2018, 'C', 'MTRF', 900, 950);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'A', 'MTRF', 1400, 1450);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'B', 'MTWRF', 1300, 1350);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'B', 'MR', 1600, 1750);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'C', 'MTRF', 1400, 1450);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'D', 'MTRF', 1400, 1450);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'A', 'MTRF', 900, 950);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'A', 'MTRF', 1500, 1550);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'B', 'MR', 1200, 1350);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'C', 'MTRF', 1500, 1550);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'C', 'MTRF', 1600, 1650);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'D', 'MTRF', 1600, 1650);
INSERT INTO calendar_info(cal_year, cal_term, days, start_time, end_time)
VALUES(2018, 'D', 'F', 800, 1050);

INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('A01', 01, 'LECTURE', 100, 2, 17, 65);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('B01', 01, 'LECTURE', 100, 6, 17, 66);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('B01', 01, 'LECTURE', 100, 1, 17, 67);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('C01', 01, 'LECTURE', 100, 12, 17, 68);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('C01', 01, 'LECTURE', 100, 2, 17, 69);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('D01', 01, 'LECTURE', 100, 6, 17, 70);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('A01', 01, 'LECTURE', 100, 38, 33, 71);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('A01', 01, 'LECTURE', 100, 29, 33, 72);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('B01', 01, 'LECTURE', 100, 14, 33, 73);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('B01', 01, 'LECTURE', 100, 4, 33, 74);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('C01', 01, 'LECTURE', 100, 11, 33, 75);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('C02', 02, 'LECTURE', 100, 2, 33, 76);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('D01', 01, 'LECTURE', 100, 4, 33, 77);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES ('D02', 02, 'LECTURE', 100, 6, 33, 78);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('A01', 01, 'LECTURE', 50, 3, 7, 79);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('A01', 01, 'LECTURE', 50, 1, 7, 80);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('B01', 01, 'LECTURE', 50, 17, 7, 81);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('B01', 01, 'LECTURE', 50, 24, 7, 82);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('C01', 01, 'LECTURE', 50, 33, 7, 83);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('C01', 01, 'LECTURE', 50, 14, 7, 84);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('A01', 01, 'LECTURE', 50, 12, 42, 85);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('B02', 02, 'LECTURE', 50, 14, 42, 86);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('B02', 02, 'LECTURE', 50, 40, 42, 87);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('C02', 02, 'LECTURE', 50, 18, 42, 88);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('D01', 01, 'LECTURE', 50, 12, 42, 89);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('A03', 03, 'LECTURE', 50, 1, 29, 90);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('A01', 01, 'LECTURE', 25, 13, 29, 91);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('B01', 01, 'LECTURE', 100, 1, 29, 92);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('C01', 01, 'LECTURE', 50, 18, 29, 93);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('C02', 02, 'LECTURE', 50, 4, 29, 94);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('D01', 01, 'LECTURE', 50, 8, 29, 95);
INSERT INTO course_sections(section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES('D01', 01, 'LECTURE', 50, 13, 29, 96);

INSERT INTO users_roles_links(user_id, role_id) VALUES (12, 2);
INSERT INTO users_roles_links(user_id, role_id) VALUES (13, 1);
INSERT INTO users_roles_links(user_id, role_id) VALUES (14, 1);
INSERT INTO users_roles_links(user_id, role_id) VALUES (15, 2);
INSERT INTO users_roles_links(user_id, role_id) VALUES (16, 1);