create table account(
  id serial primary key,
  owner bigint not null references `user`(id),
  name text not null,
  initial_balance decimal(13, 4) not null default 0
);