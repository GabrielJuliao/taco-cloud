-- for mysqldb
create database if not exists Taco_Cloud;
use Taco_cloud;
create table if not exists Ingredient
(
mysql     id   int primary key AUTO_INCREMENT,
    name varchar(255) not null,
    type varchar(255) not null
);

create table if not exists Taco
(
    id        int primary key AUTO_INCREMENT,
    name      varchar(50) not null,
    createdAt timestamp   not null
);

create table if not exists Taco_Ingredients
(
    taco       int not null,
    ingredient int not null,
    foreign key (taco) references Taco (id),
    foreign key (ingredient) references Ingredient (id)
);

create table if not exists Taco_Order
(
    id             int primary key AUTO_INCREMENT,
    deliveryName   varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity   varchar(50) not null,
    deliveryState  varchar(2)  not null,
    deliveryZip    varchar(10) not null,
    ccNumber       varchar(16) not null,
    ccExpiration   varchar(5)  not null,
    ccCVV          varchar(3)  not null,
    placedAt       timestamp   not null
);

create table if not exists Taco_Order_Tacos
(
    tacoOrder int not null,
    taco      int not null,
    foreign key (tacoOrder) references Taco_Order (id),
    foreign key (taco) references Taco (id)
);

create table if not exists users
(
    id           int primary key auto_increment,
    username     varchar(255) unique not null,
    password     varchar(255)        not null,
    full_name    varchar(255),
    street       varchar(255),
    city         varchar(255),
    state        varchar(255),
    zip          varchar(255),
    phone_number varchar(15),
    enabled      bit default 1
);

create table if not exists authorities
(
    id        int primary key AUTO_INCREMENT,
    username  varchar(255),
    authority varchar(50) NOT NULL default 'ROLE_USER',
    foreign key (username) references users (username)
);