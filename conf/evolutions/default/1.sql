# --- First database schema

# --- !Ups

create table intervention (
  id                        bigint not null,
  name                      varchar(255),
  introduced                timestamp,
  scheduled              timestamp,
  application_id                bigint,
  constraint pk_intervention primary key (id))
;


create sequence intervention_seq start with 1000;

create table department (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_department primary key (id))
;


create sequence department_seq start with 1000;

create table application (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_application primary key (id))
;


create sequence application_seq start with 1000;
# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists application;
drop table if exists department;
drop table if exists intervention;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists application_seq;
drop sequence if exists department_seq;
drop sequence if exists intervention_seq;

