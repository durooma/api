create table label(
  id serial primary key,
  owner bigint references `user`(id),
  name varchar(128) not null
);

create table transaction_label(
  id serial primary key,
  transaction_id bigint not null references transaction(id),
  label_id bigint not null references label(id)
);