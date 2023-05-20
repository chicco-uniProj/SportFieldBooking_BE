DROP SCHEMA rovito;
CREATE SCHEMA rovito;
USE rovito;

CREATE TABLE user (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    telephone VARCHAR(20),
    email VARCHAR(90)

);

CREATE TABLE court (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    type VARCHAR(50),
    city VARCHAR(50),
    price_hourly FLOAT,
    description VARCHAR(500),
    version LONG
);

CREATE TABLE booking (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    data VARCHAR(15),
    prezzo FLOAT,
    buyer INTEGER,
    court INTEGER,
    purchase_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (buyer) REFERENCES user(id),
    FOREIGN KEY (court) REFERENCES court(id)


);


