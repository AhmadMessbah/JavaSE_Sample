create table person_tbl(
    id number primary key ,
    name nvarchar2(30),
    family nvarchar2(30),
    gender varchar2(6),
    birth_date date,
    city varchar2(20),
    algo number(1),
    se number(1),
    ee number(1),
    user_id references user_tbl
);

create sequence person_seq  start with 1 increment by 1;


create table user_tbl(
                       id number primary key ,
                       username nvarchar2(30) unique,
                       password nvarchar2(30),
                       enabled number(1)
);

create sequence user_seq  start with 1 increment by 1;