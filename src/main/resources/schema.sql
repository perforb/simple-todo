create table todo (
   id         int         not null auto_increment
  ,title      varchar(30) not null
  ,done       boolean     not null
  ,created_at timestamp   not null
  ,primary key(id)
);
