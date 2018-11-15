use simple_todo;

start transaction;

insert into user (user_name, user_id, password, created_at) values ('Walter', 'white', '$2a$10$BqPavzCrjiN/OXRtAzSSXO.SAlRAXLUMcSLkTCaRGqcIMtaG6t5xi', '2018-05-30 00:00:00');
insert into user (user_name, user_id, password, created_at) values ('Jesse',  'jesse', '$2a$10$BqPavzCrjiN/OXRtAzSSXO.SAlRAXLUMcSLkTCaRGqcIMtaG6t5xi', '2018-05-30 00:00:00');

insert into task (title, done, created_at) values ('setup', 0, '2018-05-30 00:00:00');
insert into task (title, done, created_at) values ('development', 0, '2018-05-30 00:00:00');
insert into task (title, done, created_at) values ('test', 0, '2018-05-30 00:00:00');
insert into task (title, done, created_at) values ('deploy', 0, '2018-05-30 00:00:00');

commit;
