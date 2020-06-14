drop table if exists documents cascade;
drop table if exists users cascade;

create table users
(
    id         serial not null,
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
);


create table documents
(
    id          serial not null,
    expiry_date date,
    number      varchar(255),
    title       varchar(255),
    user_id     serial,
    primary key (id)
);