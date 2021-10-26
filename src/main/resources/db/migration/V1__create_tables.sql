create table person
(
    id       bigint auto_increment,
    name     char not null,
    surname  char not null,
    login    char not null,
    password char not null
);

create unique index USER_ID_UINDEX
    on person (id);

create unique index USER_LOGIN_UINDEX
    on person (login);

alter table person
    add constraint USER_PK
        primary key (id);

create table role
(
    id   bigint auto_increment,
    name char not null
);

create unique index ROLE_ID_UINDEX
    on role (id);

create unique index ROLE_NAME_UINDEX
    on role (name);

alter table role
    add constraint ROLE_PK
        primary key (id);

create table person_has_role
(
    personId bigint not null,
    roleId   bigint not null

);

alter table person_has_role
    add constraint PERSON_HAS_ROLE_PK
        primary key (personId, roleId);

alter table person_has_role
    add foreign key (personId) references person (id);

alter table person_has_role
    add foreign key (roleId) references role (id);

create table service
(
    id    bigint auto_increment,
    name  char   not null,
    price double not null
);

create unique index SERVICE_ID_UINDEX
    on service (id);

create unique index SERVICE_NAME_UINDEX
    on service (name);

alter table service
    add constraint SERVICE_PK
        primary key (Id);

create table record
(
    id        bigint auto_increment,
    date      date not null,
    startTime timestamp not null,
    endTime   timestamp not null,
    clientId  bigint  not null,
    workerId  bigint  not null,
    serviceId bigint  not null
);

create unique index RECORD_ID_UINDEX
    on record (id);

alter table record
    add constraint RECORD_PK
        primary key (id);

alter table record
    add foreign key (clientId) references person (id);

alter table record
    add foreign key (workerId) references person (id);

alter table record
    add foreign key (serviceId) references service (id);

COMMIT;