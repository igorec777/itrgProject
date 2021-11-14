insert into role (name)
values ('Client');
insert into role (name)
values ('Admin');
insert into role (name)
values ('Worker');
insert into role (name)
values ('Manager');

insert into person (name, surname, login, password)
values ('Name1', 'Surname1', 'Login1', '1111');
insert into person (name, surname, login, password)
values ('Name2', 'Surname2', 'Login2', '2222');
insert into person (name, surname, login, password)
values ('Name3', 'Surname3', 'Login3', '3333');

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

insert into record (startTime, endTIme, clientId, workerId, serviceId)
values (parsedatetime('2021-09-25 11:20:00', 'yyyy-MM-dd HH:mm:ss'), parsedatetime('2021-09-25 13:20:00', 'yyyy-MM-dd HH:mm:ss'), 1, 2, 3);

insert into record (startTime, endTIme, clientId, workerId, serviceId)
values (parsedatetime('2021-09-20 18:35:00', 'yyyy-MM-dd HH:mm:ss'), parsedatetime('2021-09-20 19:00:00', 'yyyy-MM-dd HH:mm:ss'), 2, 1, 2);

