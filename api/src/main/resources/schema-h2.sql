create table user (
   id         bigint       not null auto_increment
  ,user_name  varchar(255) not null
  ,user_id    varchar(255) not null
  ,password   varchar(255) not null
  ,created_at timestamp    not null
  ,primary key(id)
  ,unique key(user_id)
);

create table task (
   id         bigint      not null auto_increment
  ,title      varchar(30) not null
  ,done       boolean     not null
  ,created_at timestamp   not null
  ,primary key(id)
);
