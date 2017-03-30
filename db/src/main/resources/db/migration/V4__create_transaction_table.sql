create table transaction(
  id serial primary key,
  owner bigint not null references `user`(id),
  source bigint references account(id),
  target bigint references account(id),
  amount decimal(13, 4) not null,
  huquq_amount decimal(13, 4) not null default 0
);