create table transaction(
  id serial primary key,
  date date not null,
  owner bigint not null references `user`(id),
  source bigint references account(id),
  target bigint references account(id),
  amount decimal(13, 4) not null,
  exempt decimal(13, 4) not null default 0,
  description text
);