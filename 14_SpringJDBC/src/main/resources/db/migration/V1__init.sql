create table products
(
    id    bigserial primary key,
    title varchar(255),
    price numeric(15, 2)
);

insert into products (title, price)
values ('Milk', 80.10),
       ('Bread', 35.20),
       ('Eggs', 35.20),
       ('Cherry', 35.40),
       ('Sausage', 35.55),
       ('Meat', 35.55),
       ('Fish', 35.99),
       ('Coffee', 35.50),
       ('Tea', 35.14),
       ('Cheese', 320.00);