create table person
(
    Id       int auto_increment,
    name     char not null,
    surname  char not null,
    login    char not null,
    password char not null
);

create unique index USER_ID_UINDEX
    on person (Id);

create unique index USER_LOGIN_UINDEX
    on person (login);

alter table person
    add constraint USER_PK
        primary key (Id);

create table role
(
    Id   int auto_increment,
    name char not null
);

create unique index ROLE_ID_UINDEX
    on role (Id);

create unique index ROLE_NAME_UINDEX
    on role (name);

alter table role
    add constraint ROLE_PK
        primary key (id);

create table person_has_role
(
    personId int not null,
    roleId   int not null

);

alter table person_has_role
    add constraint PERSON_HAS_ROLE_PK
        primary key (personId, roleId);

alter table person_has_role
    add foreign key (personId) references person (Id);

alter table person_has_role
    add foreign key (roleId) references role (Id);

create table service
(
    Id    int auto_increment,
    name  char   not null,
    price double not null
);

create unique index SERVICE_ID_UINDEX
    on service (Id);

create unique index SERVICE_NAME_UINDEX
    on service (name);

alter table service
    add constraint SERVICE_PK
        primary key (Id);

create table record
(
    Id        int auto_increment,
    date      date not null,
    startTime time not null,
    endTime   time,
    clientId  int  not null,
    workerId  int  not null,
    serviceId int  not null
);

create unique index RECORD_ID_UINDEX
    on record (Id);

alter table record
    add constraint RECORD_PK
        primary key (Id);

alter table record
    add foreign key (clientId) references person (Id);

alter table record
    add foreign key (workerId) references person (Id);

alter table record
    add foreign key (serviceId) references service (Id);

COMMIT;