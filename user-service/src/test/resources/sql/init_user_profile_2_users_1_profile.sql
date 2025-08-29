insert into "user" (id, email, first_name, last_name) values (1, 'luis@gmail.com', 'Fallen', 'Awp');
insert into "user" (id, email, first_name, last_name) values (2, 'luisdawdawd@gmail.com', 'Faldawdasslen', 'Awssp');
insert into "profile" (id, description, name) values (2, 'Regular user with regular permissions', 'Regular User');
insert into "user_profile" (id, user_id, profile_id) values (1, 1, 2);
insert into "user_profile" (id, user_id, profile_id) values (2, 2, 2);
