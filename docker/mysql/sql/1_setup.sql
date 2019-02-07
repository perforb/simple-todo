create database if not exists simple_todo
  character set = utf8mb4
  collate = utf8mb4_general_ci;

-- ref to https://dev.mysql.com/doc/refman/8.0/en/create-user.html
create user 'tron'@'%'
  identified with mysql_native_password by 'pass'
--  identified with caching_sha2_password by 'pass'
  password expire never;

grant all on simple_todo.* to 'tron'@'%';

use simple_todo;

create table if not exists `user` (
   id         bigint       not null auto_increment
  ,user_name  varchar(255) not null
  ,user_id    varchar(255) not null
  ,password   varchar(255) not null
  ,created_at timestamp    not null
  ,primary key(id)
  ,unique key(user_id)
);

create table if not exists `task` (
   id         bigint       not null auto_increment
  ,title      varchar(255) not null
  ,done       boolean      not null
  ,created_at timestamp    not null
  ,primary key(id)
);
