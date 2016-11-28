CREATE TABLE IF NOT EXISTS category
(
  id INT PRIMARY KEY,
  name VARCHAR(40),
  description VARCHAR(100),
  department VARCHAR(10)
);


CREATE TABLE IF NOT EXISTS supplier
(
  id INT PRIMARY KEY,
  name VARCHAR(40),
  description VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS product
(
  id INT PRIMARY KEY,
  name VARCHAR(40),
  description VARCHAR(100),
  defaultPrice INT,
  defaultCurrency VARCHAR(10),
  categoryId INT,
  FOREIGN KEY (categoryId) REFERENCES category(id),
  supplierId INT,
  FOREIGN KEY (supplierId) REFERENCES supplier(id)
);

