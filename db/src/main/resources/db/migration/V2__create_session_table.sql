create table session(
  id serial primary key,
  user_id bigint not null references `user`(id),
  token varchar(128) not null unique,
  expires_at timestamp not null
);