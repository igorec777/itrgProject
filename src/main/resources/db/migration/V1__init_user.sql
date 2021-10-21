create table user
(
    Id int auto_increment,
    firstName char not null,
    lastName char not null,
    login char not null,
    passwordHash char not null
);

create unique index USER_ID_UINDEX
    on user (Id);

create unique index USER_LOGIN_UINDEX
    on user (login);

alter table user
    add constraint USER_PK
        primary key (Id);

insert into user (firstName, lastName, login, passwordHash) values ('Name1', 'Surname1', 'Login1', '1111');
insert into user (firstName, lastName, login, passwordHash) values ('Name2', 'Surname2', 'Login2', '2222');
insert into user (firstName, lastName, login, passwordHash) values ('Name3', 'Surname3', 'Login3', '3333');

COMMIT;