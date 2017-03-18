create table account(
  id serial primary key,
  owner int references `user`(id),
  name text not null,
  initial_balance double precision not null default 0
);