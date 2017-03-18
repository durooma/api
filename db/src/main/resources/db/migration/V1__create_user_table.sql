create table user(
  id serial primary key,
  first_name text,
  last_name text,
  email varchar(128) unique not null,
  password varchar(256)
);