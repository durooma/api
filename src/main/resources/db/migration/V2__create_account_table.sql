create table account(
  id serial primary key,
  name text not null,
  initial_balance double precision not null default 0
);