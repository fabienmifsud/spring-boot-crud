
CREATE SEQUENCE client_seq START 1;

CREATE TABLE IF NOT EXISTS client (
    id int PRIMARY KEY,
    name varchar(20),
    email varchar(50),
    date_of_birth timestamp
);

CREATE SEQUENCE product_seq START 1;

CREATE TABLE IF NOT EXISTS product (
    id int PRIMARY KEY,
    name varchar(20),
    price numeric
    );

CREATE SEQUENCE booking_seq START 1;

CREATE TABLE IF NOT EXISTS booking (
    id int PRIMARY KEY,
    client_id int,
    product_id int,
    booking_date timestamp,
    CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES client(id),
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES product(id)
    );