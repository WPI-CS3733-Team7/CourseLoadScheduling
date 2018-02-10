CREATE TABLE user_states
(
	id serial PRIMARY KEY,
	state varchar(255) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	unique(state, deleted)
);


CREATE TABLE users
(
	id serial PRIMARY KEY,
	user_name varchar(255) UNIQUE NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) UNIQUE NOT NULL,
	encrypted_password varchar(255) NOT NULL,
	salt varchar(255) UNIQUE NOT NULL,
	user_state_id integer NOT NULL REFERENCES user_states(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);


CREATE UNIQUE INDEX users_user_name ON users(user_name);


CREATE TABLE users_history
(
	id serial PRIMARY KEY,
	user_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	user_name varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	encrypted_password varchar(255) NOT NULL,
	salt varchar(255) NOT NULL,
	user_state_id integer NOT NULL REFERENCES user_states(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);


CREATE TABLE user_roles
(
	id serial PRIMARY KEY,
	role_name varchar(255) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	UNIQUE(role_name, deleted)
);


CREATE TABLE users_roles_links
(
	id serial PRIMARY KEY,
	user_id integer NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	role_id integer NOT NULL REFERENCES user_roles(id) ON DELETE CASCADE,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE),
	UNIQUE(user_id, role_id, deleted)
);

/*
New Tables Begin Here
*/

CREATE TABLE courses
(
	id serial PRIMARY KEY,
	course_name varchar(255) NOT NULL,
	course_number varchar(255) NOT NULL,
	frequency varchar(255) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE calendar_info
(
	id serial PRIMARY KEY,
	cal_year integer NOT NULL,
	cal_term varchar(255) NOT NULL,
	days varchar(255) NOT NULL,
	start_time integer NOT NULL,
	end_time integer NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE request_types
(
	id serial PRIMARY KEY,
	request_type varchar(255) NOT NULL
);

CREATE TABLE reply_types
(
  id serial PRIMARY KEY,
  reply_type varchar(255) NOT NULL,
  deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE instructors
(
	id serial PRIMARY KEY,
	rank varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE requests
(
	id serial PRIMARY KEY,
	requester_id integer NOT NULL REFERENCES instructors(id),
	request_type_id integer NOT NULL REFERENCES request_types(id),
	request_details varchar(255) NOT NULL,
	reply_type_id integer NOT NULL REFERENCES reply_types(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE course_loads
(
	id serial PRIMARY KEY,
	load_type varchar(255) NOT NULL,
	load_description varchar(255) NOT NULL,
	instructor_id integer NOT NULL REFERENCES instructors(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE course_sections
(
	id serial PRIMARY KEY,
	section_name varchar(255) NOT NULL,
	section_id integer NOT NULL,
	section_type varchar(255) NOT NULL,
	population integer NOT NULL,
	course_id integer NOT NULL  REFERENCES courses(id),
	instructor_id integer NOT NULL  REFERENCES instructors(id),
	calendar_info_id integer NOT NULL  REFERENCES calendar_info(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

CREATE TABLE instructor_user_links
(
	id serial PRIMARY KEY,
	instructor_id integer NOT NULL REFERENCES instructors(id),
	linked_user_id integer NOT NULL REFERENCES users(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	updated_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	deleted boolean NOT NULL DEFAULT(FALSE)
);

/*
Database History Below
*/

CREATE TABLE courses_history
(
	id serial PRIMARY KEY,
	former_id integer NOT NULL REFERENCES courses(id),
	course_name varchar(255) NOT NULL,
	course_number varchar(255) NOT NULL,
	frequency varchar(255) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

CREATE TABLE calendar_info_history
(
	id serial PRIMARY KEY,
	former_id integer NOT NULL REFERENCES calendar_info(id),
	cal_year integer NOT NULL,
	cal_term varchar(255) NOT NULL,
	days varchar(255) NOT NULL,
	start_time integer NOT NULL,
	end_time integer NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

CREATE TABLE instructors_history
(
	id serial PRIMARY KEY,
	former_id integer NOT NULL REFERENCES instructors(id),
	rank varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

CREATE TABLE course_loads_history
(
	id serial PRIMARY KEY,
	former_id integer NOT NULL REFERENCES course_loads(id),
	load_type varchar(255) NOT NULL,
	load_description varchar(255) NOT NULL,
	instructor_id integer NOT NULL REFERENCES instructors(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

CREATE TABLE course_sections_history
(
	id serial PRIMARY KEY,
	former_id integer NOT NULL REFERENCES course_sections(id),
	section_name varchar(255) NOT NULL,
	section_id integer NOT NULL,
	section_type varchar(255) NOT NULL,
	population integer NOT NULL,
	course_id integer NOT NULL  REFERENCES courses(id),
	instructor_id integer NOT NULL  REFERENCES instructors(id),
	calendar_info_id integer NOT NULL  REFERENCES calendar_info(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

CREATE TABLE instructor_user_links_history
(
	id serial PRIMARY KEY,
	former_id integer NOT NULL REFERENCES instructor_user_links(id),
	instructor_id integer NOT NULL REFERENCES instructors(id),
	linked_user_id integer NOT NULL REFERENCES users(id),
	created_at timestamp with time zone NOT NULL DEFAULT(CURRENT_TIMESTAMP)
);

/*
History Insertion Functions Below
*/

CREATE FUNCTION insert_users_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO users_history(user_id, user_name, first_name, last_name, email, encrypted_password, salt, user_state_id)
VALUES(OLD.id, OLD.user_name, OLD.first_name, OLD.last_name, OLD.email, OLD.encrypted_password, OLD.salt, OLD.user_state_id);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE FUNCTION insert_courses_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO courses_history(former_id, course_name, course_number, frequency)
VALUES(OLD.id, OLD.course_name, OLD.course_number, OLD.frequency);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;	

CREATE FUNCTION insert_calendar_info_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO calendar_info_history(former_id, cal_year, cal_term, days, start_time, end_time)
VALUES(OLD.id, OLD.cal_year, OLD.cal_term, OLD.days, OLD.start_time, OLD.end_time);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE FUNCTION insert_instructors_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO instructors_history(former_id, rank, first_name, last_name, email)
VALUES(OLD.id, OLD.rank, OLD.first_name, OLD.last_name, OLD.email);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE FUNCTION insert_course_loads_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO course_loads_history(former_id, load_type, load_description, instructor_id)
VALUES(OLD.id, OLD.load_type, OLD.load_description, OLD.instructor_id);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;	

CREATE FUNCTION insert_course_sections_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO course_sections_history(former_id, section_name, section_id, section_type, population, course_id, instructor_id, calendar_info_id)
VALUES(OLD.id, OLD.section_name, OLD.section_id, OLD.section_type, OLD.population, OLD.course_id, OLD.instructor_id, OLD.calendar_info_id);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE FUNCTION insert_instructor_user_links_history() RETURNS TRIGGER AS
$BODY$
BEGIN
INSERT INTO instructor_user_links_history(former_id, instructor_id, linked_user_id)
VALUES(OLD.id, OLD.instructor_id, OLD.linked_user_id);
RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;	

/*
History Update Triggers Below
*/

CREATE TRIGGER update_users
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE PROCEDURE insert_users_history();

CREATE TRIGGER update_courses
BEFORE UPDATE ON courses
FOR EACH ROW
EXECUTE PROCEDURE insert_courses_history();

CREATE TRIGGER update_calendar_info
BEFORE UPDATE ON calendar_info
FOR EACH ROW
EXECUTE PROCEDURE insert_calendar_info_history();

CREATE TRIGGER update_instructors
BEFORE UPDATE ON instructors
FOR EACH ROW
EXECUTE PROCEDURE insert_instructors_history();

CREATE TRIGGER update_course_loads
BEFORE UPDATE ON course_loads
FOR EACH ROW
EXECUTE PROCEDURE insert_course_loads_history();

CREATE TRIGGER update_course_sections
BEFORE UPDATE ON course_sections
FOR EACH ROW
EXECUTE PROCEDURE insert_course_sections_history();

CREATE TRIGGER update_instructor_user_links
BEFORE UPDATE ON instructor_user_links
FOR EACH ROW
EXECUTE PROCEDURE insert_instructor_user_links_history();

----------------------------------------------------------------------------------------------------------------------

/*
DROP TRIGGER update_users ON users;
DROP FUNCTION insert_users_history();

DROP TABLE users_roles_links;
DROP TABLE user_roles;
DROP TABLE users_history;
DROP TABLE users;
DROP TABLE user_states;
*/