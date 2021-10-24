insert into person (name, surname, login, password)
values ('Name1', 'Surname1', 'Login1', '1111');
insert into person (name, surname, login, password)
values ('Name2', 'Surname2', 'Login2', '2222');
insert into person (name, surname, login, password)
values ('Name3', 'Surname3', 'Login3', '3333');

insert into role (name)
values ('ADMIN');
insert into role (name)
values ('WORKER');
insert into role (name)
values ('CLIENT');

insert into person_has_role (personId, roleId)
values (1, 1);
insert into person_has_role (personId, roleId)
values (1, 2);

insert into person_has_role (personId, roleId)
values (2, 2);
insert into person_has_role (personId, roleId)
values (2, 3);

insert into person_has_role (personId, roleId)
values (3, 1);
insert into person_has_role (personId, roleId)
values (3, 3);

insert into service (name, price)
values ('Peeling', 50.4);
insert into service (name, price)
values ('Sugaring', 40.2);
insert into service (name, price)
values ('Pedicure', 65.8);

insert into record (date, startTime, endTime, clientId, workerId, serviceId)
values ('2021-10-26', '12:20:00', '14:20:00', 1, 2, 3);
insert into record (date, startTime, endTime, clientId, workerId, serviceId)
values ('2021-10-27', '18:35:00', '19:00:00', 3, 2, 2);

COMMIT;