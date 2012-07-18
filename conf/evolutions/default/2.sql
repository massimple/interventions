# --- Sample dataset

# --- !Ups

insert into intervention (id,name) values (  1,'Massimple Website');
insert into intervention (id,name) values (  2,'GitHub signin');

insert into department (id,name) values (  1,'Unix Admin');
insert into department (id,name) values (  2,'Windows Admin');

insert into application (id,name) values (  1,'Web Portal');
insert into application (id,name) values (  2,'Web services module');
insert into application (id,name) values (  3,'Back end module');

insert into ldeptinterv (idintervention_id, department_id, ) values (1,1);
insert into ldeptinterv (idintervention_id, department_id, ) values (1,2);

insert into ldeptinterv (idintervention_id, department_id, ) values (2,1);

# --- !Downs

delete from ldeptinterv;
delete from intervention;
delete from department;
delete from application;