CREATE TABLE users (
  username VARCHAR(255) PRIMARY KEY,
  password VARCHAR(255),
  email VARCHAR(255),
  userRole VARCHAR(255)
);

CREATE TABLE pizza (
  name VARCHAR(255) PRIMARY KEY,
  size VARCHAR(255),
  crust VARCHAR(255),
  price FLOAT,
  description VARCHAR(255)
);

CREATE TABLE cart (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  size VARCHAR(255),
  crust VARCHAR(255),
  quantity int,
  price FLOAT
);
