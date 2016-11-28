DROP TABLE IF EXISTS category;

CREATE TABLE category
(
  id INT PRIMARY KEY,
  name VARCHAR(40),
  description VARCHAR(100),
  department VARCHAR(10)
);

DROP TABLE IF EXISTS supplier;

CREATE TABLE supplier
(
  id INT PRIMARY KEY,
  name VARCHAR(40),
  description VARCHAR(100)
);

DROP TABLE IF EXISTS product;

CREATE TABLE product
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

