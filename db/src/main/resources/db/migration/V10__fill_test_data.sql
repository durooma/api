insert into `user`(id, first_name, last_name, email, `password`) values
  (1, 'Hannes', 'Widmoser', 'widmoser@gmail.com', '$2a$10$VJAa.j9LNTbob.6yLbqWQeEj31TGvW0g7pce9//UE1Vxotx.U9QmS');

insert into `account`(id, `owner`, `name`, initial_balance) values
  (1, 1, 'Raiffeisen', 458.53),
  (2, 1, 'Cash', 42.12);