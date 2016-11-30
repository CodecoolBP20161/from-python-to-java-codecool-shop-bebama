CREATE TABLE IF NOT EXISTS category
(
  c_id SERIAL PRIMARY KEY,
  c_name TEXT,
  department TEXT,
  c_description TEXT
);


CREATE TABLE IF NOT EXISTS supplier
(
  s_id SERIAL PRIMARY KEY,
  s_name TEXT,
  s_description TEXT
);


CREATE TABLE IF NOT EXISTS product
(
  p_id SERIAL PRIMARY KEY,
  p_name TEXT,
  P_description TEXT,
  defaultprice TEXT,
  defaultcurrency TEXT,
  categoryid INT,
  supplierid INT);