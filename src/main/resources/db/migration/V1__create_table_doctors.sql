create table doctors (

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    crm varchar(6) not null unique,
    birth_date DATE not null,
    phone varchar(20) not null,
    speciality varchar(100) not null,
    active tinyint default 1,

    street varchar(100) not null,
    number varchar(20),
    city varchar(100) not null,
    state char(2) not null,
    zip_code varchar(9) not null,

    primary key(id)

);
