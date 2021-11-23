--person

create table person
(
    id       bigint auto_increment,
    name     char not null,
    surname  char not null,
    login    char not null,
    password char not null
);

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

create unique index ROLE_NAME_UINDEX
    on role (name);

--role

alter table role
    add constraint ROLE_PK
        primary key (id);

create table person_has_role
(
    person_id bigint not null,
    role_id   bigint not null

);

alter table person_has_role
    add constraint PERSON_HAS_ROLE_PK
        primary key (person_id, role_id);

alter table person_has_role
    add foreign key (person_id) references person (id);

alter table person_has_role
    add foreign key (role_id) references role (id);

--service

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

--record

create table record
(
    id        bigint auto_increment,
    start_time timestamp not null,
    end_time   timestamp not null,
    client_id  bigint  not null,
    worker_id  bigint  not null,
    service_id bigint  not null
);

create unique index RECORD_ID_UINDEX
    on record (id);

alter table record
    add constraint RECORD_PK
        primary key (id);

alter table record
    add foreign key (client_id) references person (id);

alter table record
    add foreign key (worker_id) references person (id);

alter table record
    add foreign key (service_id) references service (id);

COMMIT;