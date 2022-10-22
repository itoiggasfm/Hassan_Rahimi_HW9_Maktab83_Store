create database store;

CREATE TYPE person_role AS ENUM ('ADMIN', 'SALESMAN', 'CUSTOMER');

CREATE TYPE product_type AS ENUM ('ELECTRONIC', 'SHOE', 'PRINTED_MATTER');

CREATE TYPE order_status AS ENUM ('OPEN', 'DELIVERED');

create table tbl_product
(
    id serial primary key,
    name varchar,
    price float(2),
    quantity int,
    product_type product_type
);

create table tbl_person
(
    id varchar(10) unique primary key,
    name varchar,
    user_name varchar unique,
    password varchar,
    person_role person_role
);

insert into tbl_person (id, name, user_name, password, user_role) values ('0011111112', 'admin', 'admin', 'admin', 'ADMIN');

create table tbl_order
(
    item_id int UNIQUE PRIMARY KEY,
    id varchar(20) ,
    person_id varchar(10),
    product_id int,
    product_type product_type,
    item_quantity int,
    item_price float(2),
    total_item_price float(2),
    order_date varchar,
    deliver_date varchar,
    order_status order_status,
    salesman varchar,
    constraint fk_userid
        foreign key (person_id)
            references tbl_person(id),
    constraint  fk_productid
        foreign key (product_id)
            references tbl_product(id)
);
