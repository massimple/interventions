# --- Sample dataset

# --- !Ups

insert into application (id,name) values (1,'Web Portal');
insert into application (id,name) values (2,'Web services module');
insert into application (id,name) values (3,'Back end module');

insert into intervention (id,name,application_id,introduced,scheduled) values (1,'massimple website 2.0 go live',1,
	PARSEDATETIME('07-07-2012', 'dd-MM-yyyy'),
	PARSEDATETIME('21-07-2012', 'dd-MM-yyyy'));

insert into intervention (id,name,application_id,introduced,scheduled) values (2,'massimple business modules', 2,
	PARSEDATETIME('11-08-2012', 'dd-MM-yyyy'),
	PARSEDATETIME('25-08-2012', 'dd-MM-yyyy'));

insert into department (id,name) values (1,'Unix Admin');
insert into department (id,name) values (2,'Windows Admin');

insert into ldeptinterv (idintervention_id, department_id ) values (1,1);
insert into ldeptinterv (idintervention_id, department_id ) values (1,2);
insert into ldeptinterv (idintervention_id, department_id ) values (2,1);

# --- !Downs

delete from ldeptinterv;
delete from intervention;
delete from department;
delete from application;