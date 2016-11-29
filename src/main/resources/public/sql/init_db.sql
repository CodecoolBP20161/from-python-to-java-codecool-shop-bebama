CREATE TABLE IF NOT EXISTS category
(
  id SERIAL PRIMARY KEY,
  name TEXT,
  department TEXT,
  description TEXT
);


CREATE TABLE IF NOT EXISTS supplier
(
  id SERIAL PRIMARY KEY,
  name TEXT,
  description TEXT
);


CREATE TABLE IF NOT EXISTS product
(
  id SERIAL PRIMARY KEY,
  name TEXT,
  description TEXT,
  defaultPrice FLOAT,
  defaultCurrency TEXT,
  categoryId INT,
  FOREIGN KEY (categoryId) REFERENCES category(id),
  supplierId INT,
  FOREIGN KEY (supplierId) REFERENCES supplier(id)
);