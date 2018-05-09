use kincurrently_db;

INSERT INTO families (code, name) VALUES ('barrera','Barrera'),('scott','Scott');

INSERT INTO users (birthdate, email, first_name, last_name, password, title, username, family_id) VALUES
  ('1983-02-07', 'rodger.scott@gmail.com', 'Rodger', 'Scott', 'rodger', 'Dad', 'Rodger', 2),
  ('1989-07-16', 'lynnette@gmail.com', 'Lynette', 'Barrera', 'lynette', 'Mom', 'Lyna', 1),
  ('1999-04-04', 'marcy@marcy.com', 'Marcy', 'Barrera', 'marcy', 'Lil Sis', 'Marcy', 1),
  ('2016-10-16', 'edgar.scott@gmail.com', 'Edgar', 'Scott', 'edgar', 'Son', 'Edgar', 2),
  ('1983-04-02', 'erin.scott@gmail.com', 'Erin', 'Scott', 'erin', 'Mom', 'Erin', 2);


INSERT INTO categories (name) VALUES ('Party'),('Sports'),('Anniversary'),('Birthday'),('Holiday'),('Chore'),('Shopping');



INSERT INTO events (address, description, end_date, end_time, location, start_date, start_time, title, family_id, user_id)
    VALUES ('600 W Navarro San Antonio',	'It''s Gonna Be Great!',	'2018-05-09', null,	'Grandma''s House',	'2018-05-09',	'18:00:00',	'Party at Grandma''s',	2,	1),
      ('11610 Vance Jackson',	'It''s Gonna Be Great!',	'2018-05-09', null,	'The Spot',	'2018-05-09',	'18:00:00',	'Graduation Bash',	2,	1),
      ('600 W Navarro San Antonio',	'It''s Gonna Be Great!',	'2018-05-09', null,	'Grandma''s House',	'2018-05-09',	'18:00:00',	'Party at Grandma''s',	1,	2),
      ('11610 Vance Jackson',	'It''s Gonna Be Great!',	'2018-05-09', null,	'The Spot',	'2018-05-09',	'18:00:00',	'Graduation Bash',	1,	2);


