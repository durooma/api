CREATE VIEW account_with_balance AS
  SELECT
    a.id,
    a.name,
    a.owner,
    a.initial_balance,
    a.initial_balance + coalesce(sum(a.amount), 0) AS balance
  FROM (
         SELECT
           a.*,
           CASE WHEN t.source = a.id
             THEN -t.amount
           ELSE t.amount END AS amount
         FROM account AS a
           LEFT JOIN `transaction` AS t ON t.source = a.id OR t.target = a.id
       ) a
  GROUP BY a.id
