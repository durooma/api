insert into `user`(id, first_name, last_name, email, `password`) values
  (1, 'Hannes', 'Widmoser', 'widmoser@gmail.com', '$2a$10$VJAa.j9LNTbob.6yLbqWQeEj31TGvW0g7pce9//UE1Vxotx.U9QmS'),
  (2, 'Adrian', 'Maldet', 'adrian@tinydesk.at', '$2a$10$5pLxzxOl9s4PAQmTr8BjgOew9A6LoClFeL2UWu65nrpJSq2VZNSPS');

insert into `account`(id, `owner`, `name`, initial_balance) values
  (1, 1, 'Raiffeisen', 458.53),
  (2, 1, 'Cash', 42.12);

insert into `transaction`(`date`, `owner`, `source`, target, amount, exempt, description) VALUES
  ('2017-04-03', 1, 1, null, 45.46, 0, 'Essen'),
  ('2017-04-05', 1, 2, null, 5.99, 0, 'Frühstück'),
  ('2017-04-06', 1, null, 1, 2999.00, 0, 'Lohn März 2017'),
  ('2017-04-10', 1, 1, 2, 90.00, 0, 'Bankomatabhebung');

insert into session(user_id, token, expires_at) values
  (1, 'fVYp4+MQ5HT5TPSLzCFDIK+9QgR87uRlnmHCEcuqFB+FNg59PeHLIXLn/E84TMdDp9LSzVtTYHAUY1gfqanw5Q==', now() + interval 10 year),
  (2, 'CG+XRQj7dQy8B5as7ObFNgooqj2I0pvjmU51Fvvw3VxROEmaB7o04Sg8kk6BtQbb8W+KXYcN7HqiQRCLMKAecQ==', now() + interval 10 year);