create table user(
  id serial primary key,
  first_name text,
  last_name text,
  email varchar(128) unique not null,
  login varchar(128) not null default 'custom',
  password varchar(256)
);