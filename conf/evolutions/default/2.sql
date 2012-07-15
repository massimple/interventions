# --- Sample dataset

# --- !Ups

insert into intervention (id,name) values (  1,'Massimple Website');
insert into intervention (id,name) values (  2,'GitHub signin');

insert into department (id,name) values (  1,'Unix Admin');
insert into department (id,name) values (  2,'Windows Admin');

insert into application (id,name) values (  1,'Web Portal');
insert into application (id,name) values (  2,'Web services module');
insert into application (id,name) values (  3,'Back end module');


# --- !Downs


delete from intervention;
delete from department;
delete from application;