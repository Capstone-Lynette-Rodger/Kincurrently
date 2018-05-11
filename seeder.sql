use kincurrently_db;

INSERT INTO families (code, name) VALUES ('familia10','Awesome'),('scott','Scott');

INSERT INTO users (birthdate, email, first_name, last_name, password, title, username, family_id, img_path) VALUES
  ('1983-02-07', 'rodger.scott@gmail.com', 'Rodger', 'Scott', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Dad', 'Rodger', 2, null),
  ('2016-10-16', 'edgar.scott@gmail.com', 'Edgar', 'Scott', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Son', 'Edgar', 2, null),
  ('1983-04-02', 'erin.scott@gmail.com', 'Erin', 'Scott', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Mom', 'Erin', 2, null),
  ('1989-07-16', 'lynette@gmail.com', 'Lynette', 'Barrera', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Lyna', 'Lyna', 1, '/uploads/lynette-img.jpg'),
  ('2007-02-27', 'adrian@gmail.com', 'Adrian', 'Paree', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', null, 'Adrian', 1, '/uploads/adrian-img.jpg'),
  ('1970-09-25', 'maribel@gmail.com', 'Maribel', 'Paree', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Mom', 'Maribel', 1, '/uploads/maribel-img.jpg'),
  ('1995-10-27', 'Jonathan@gmail.com', 'Jonathan', 'Moncivais', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Jona', 'Jonathan', 1, '/uploads/jonathan-img.jpg'),
  ('1996-07-14', 'Karen@gmail.com', 'Karen', 'Mendez', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', null, 'Karen', 1, '/uploads/karen-img.jpg'),
  ('2000-06-15', 'Alexis@gmail.com', 'Alexis', 'Moncivais', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Lexis', 'Alexis', 1, '/uploads/alexis-img.jpg'),
  ('1991-03-08', 'Lyndsey@gmail.com', 'Lyndsey', 'Fermin', '$2a$10$9HfpQWmiTmAsGSNg5vFP0e5MZ81Zs5FWMRG54NQBwXCe4WRVXBHVS', 'Lynz', 'Lyndsey', 1, '/uploads/lyndsey-img.jpg');

INSERT INTO user_roles (role, user_id) VALUES ('PARENT', 1), ('CHILD', 2), ('PARENT', 3), ('PARENT', 4), ('CHILD', 5), ('PARENT', 6), ('PARENT', 7), ('PARENT', 8), ('PARENT', 9), ('PARENT', 10);

INSERT INTO categories (name) VALUES ('Party'),('Sports'),('Anniversary'),('Birthday'),('Holiday'),('Chore'),('Shopping'),('Vacation'),('Fun'),('Accomplishment'), ('Appointment');

INSERT INTO statuses (status) VALUES ('Assigned'), ('In Progress'), ('Completed'), ('Closed');

INSERT INTO events (address, description, end_date, end_time, location, start_date, start_time, title, family_id, user_id)
    VALUES ('600 W Navarro San Antonio',	'It''s Gonna Be Great!',	'2018-05-13', null,	'Grandma''s House',	'2018-05-13',	'18:00:00',	'Party at Grandma''s',	2,	1),
      ('11610 Vance Jackson',	'It''s Gonna Be Great!',	'2018-05-12', '22:00:00',	'The Spot',	'2018-05-12',	'18:00:00',	'Graduation Bash',	2,	1),
      ('234 RR 1050, Concan, TX 78838',	'It''s Gonna Be Great! Don''t forget your bathing suits!',	'2018-05-20', null,	'Garner State Park',	'2018-05-18',	'18:00:00',	'Camping Weekend!',	1,	4),
      ('10500 Sea World Dr, San Antonio, TX 78251',	'Don''t forget your bathing suits!',	'2018-05-05', null,	'Sea World',	'2018-05-05',	'18:00:00',	'Fun Day at Sea World',	1,	6),
      ('9005 Fm 1560 North, San Antonio, TX 78254',	'Celebrate Lynette and Lyndsey''s accomplishments in their second joint graduation party! Lyndsey is graduating with her Masters in Sociology and Lynette will be graduating from a coding bootcamp, Codeup!',	'2018-05-12', '23:00:00',	'Lyndsey''s House',	'2018-05-12',	'18:00:00',	'Lynette & Lyndsey''s Graduation Bash',	1,	10),
      ('11600 FM471, San Antonio, TX 78253',	'Congratulations Alexis! Join us in watching Alexis in her high school graduation! Alexis will be attending college in the fall and plans on studying nursing!',	'2018-06-02', '18:00:00',	'Hogwarts',	'2018-06-02',	'14:00:00',	'Alexis''s Graduation',	1,	9),
      ('1200 Brooklyn Ave # 240, San Antonio, TX 78212',	'Edgar is due for a regular checkup.',	'2018-05-13', '10:00:00',	'Texas Medical',	'2018-05-13',	'09:00:00',	'Edgar''s Doctor''s Appointment',	2,	3),
      ('13102 Jones Maltsberger Rd, San Antonio, TX 78247',	'Mother''s Day 5K! Are we ready for this?!',	'2018-05-13', '09:00:00',	'McAllister Park',	'2018-05-13',	'08:00:00',	'Run This Mother 5K',	1,	4),
      ('6703 W Loop 1604 N, San Antonio, TX 78254',	'Karen and Jona''s gender reveal party! Is it a boy or girl? Come join us in finding out! Don''t forget the diapers!',	'2018-01-04',	'20:00:00',	'Lyndsey''s House',	'2018-01-04',	'16:00:00',	'Karen and Jona''s Gender Reveal',	1,	10);

INSERT INTO events_categories (event_id, category_id) VALUES (1, 1), (1, 9), (2, 1), (2, 2), (3, 8), (3, 9), (4, 8), (4, 9), (5, 1), (5, 9), (5, 10), (6, 10), (7, 11), (8, 9), (8, 10), (9, 1), (9, 9);

INSERT INTO event_comments (comment_body, created_on, event_id, user_id)
  VALUES ('Do you need help setting up beforehand? Or do you need us to bring anything?',	'2018-01-02',	9,	4),
          ('Yes I need some help please! Come by at 3pm.',	'2018-01-02',	9,	10),
          ('<div style="text-align: center;"><font size="5" face="impact">It''s started!!!</font></div><img src="https://i.imgur.com/YSStT9T.jpg" width="674"><br>',	'2018-01-04',	9,	8),
          ('<h1><font color="#0000ff" face="verdana" style="background-color: rgb(255, 255, 255);">It''s a BOY!</font></h1><img src="https://i.imgur.com/QjukMAt.jpg" width="674"><br>',	'2018-01-04',	9,	6),
          ('So cute!! I love the pictures!! I''m so excited!',	'2018-06-05',	9,	9);

INSERT INTO tasks (completed_by, created_on, description, title, creator_id, designated_user_id, status_id)
  VALUES ('2018-05-11', '2018-05-10', 'Pick up fajita, chicken, tortillas, potatoes, eggs, salsa, jalapenos, bacon, cream cheese, and beans from HEB for the graduation party.', 'Get Groceries for Party', 4, 10, 2),
  ('2018-05-13', '2018-05-10', 'Help Adrian with his solar system project. Picture of assignment details below.', 'Adrian''s Homework', 6, 7, 1),
  ('2018-05-10', '2018-05-10', 'Read one chapter from Harry Potter and the Sorcer''s Stone.', 'Reading Homework', 6, 5, 2),
  ('2018-05-10', '2018-05-10', 'Wash the dishes before I get home from work so I can start cooking.', 'Wash the Dishes', 10, 9, 3),
  ('2018-05-10', '2018-05-10', 'Wash the dishes before I get home from work so I can start cooking.', 'Wash the Dishes', 3, 1, 3),
  ('2018-05-13', '2018-05-10', 'Take Edgar to the Doctor for his checkup. Don''t forget to take his favorite toy!', 'Take Edgar to Doctor''s', 1, 3, 1),
  ('2018-05-09', '2018-05-09', 'We are having company over this weekend. Clean the backyard so we can cook out!', 'Clean Backyard', 8, 7, 2),
  ('2018-05-09', '2018-05-09', 'We are having company over this weekend. Clean the backyard so we can cook out!', 'Clean Backyard', 3, 1, 2),
  ('2018-05-20',	'2018-05-10',	'Make sure you apply for financial aid so that you can make sure to get some grant money for school!',	'Apply for FAFSA',	4,	9,	3),
  ('2018-10-13',	'2018-05-10',	'Feed and visit my little girl while I''m gone!',	'Visit Aeon',	4,	10,	1),
  ('2018-05-12',	'2018-05-10',	'I''m getting to the San Antonio Bus Stop at 3:00pm!',	'Pick Me Up!',	9,	4,	1),
  ('2018-05-19',	'2018-05-10',	'Can you pick up my shoes from La Cantera? They''re at Nordstrom.',	'Get My Shoes',	6,	4,	1),
  ('2018-05-09',	'2018-05-10',	'I''m not able to get to the store on time. Can you get it for me?',	'Buy Stroller for Jr',	7,	4,	1),
  ('2018-05-11',	'2018-05-11',	'Get online so we can play!',	'Play Mario Kart Online',	7,	5,	1);

INSERT INTO tasks_categories (task_id, category_id) VALUES (1, 1), (1, 6), (1, 7), (2, 6), (3, 6), (3, 9), (4, 6), (5, 6), (6, 6), (6, 11), (7, 6), (7, 1), (8, 6), (8, 1), (9,	1),  (9,	2), (9,	3), (9,	4), (9,	5), (9,	6), (9,	7), (9,	8), (9,	9), (9,	10), (9,	11), (10, 6), (10, 8), (11, 1), (11, 11), (12, 6), (13,	6), (13,	7);

# INSERT INTO task_comments (created_on, task_id, user_id, comment_body)
#   VALUES ();

INSERT INTO messages (body, message_read, creator_id, created_on)
  VALUES('Hey mom is there anything you need for the 5K this weekend?', false, 4, '2018-05-10 16:28:00'),
  ('Jona what time do you get off work today?', false, 8, '2018-05-10 16:27:00'),
  ('Hey Lynette can you help me with my homework?',	true,	5, '2018-05-11 17:32:25'),
  ('Yes baby bro of course! When do you want me to help?',	true,	4, '2018-05-11 18:21:45'),
  ('Come over tomorrow!',	true,	5, '2018-05-11 18:58:45'),
  ('Lyna did you see the pictures from the gender reveal?!',	false,	5, '2018-05-11 18:58:45');


INSERT INTO messages_message_recipients (message_id, recipient_id)
    VALUES (1, 6), (2, 7), (3,	4), (4,	5), (5, 4), (6, 4);
;