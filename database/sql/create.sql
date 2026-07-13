drop schema if exists kame cascade;
create schema if not exists kame;

drop schema if exists kame cascade;
create schema if not exists kame;

--account
create table kame.account(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
login_id varchar(128) not null,
password varbinary(128),
name varchar(128) not null,
primary key(id)
);

-- refresh_token
create table kame.refresh_token(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
account_id bigint not null,
token_hash varbinary(128) not null,
expire_datetime timestamp not null,
inactive_datetime timestamp,
primary key(id),
unique(account_id)
);

-- project
create table kame.project(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
account_id bigint not null,
customer_id bigint not null,
start_schedule_date date,
end_schedule_date date,
start_date date,
end_date date,
subject varchar(256) not null,
description text,
primary key(id)
);

-- task
create table kame.task(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
project_id bigint not null,
priority int,
cost double,
start_datetime timestamp,
subject varchar(256) not null,
description text,
progress int,
complete_datetime timestamp,
primary key(id)
);

-- task_to_account
create table kame.task_to_account(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
task_id bigint not null,
account_id bigint not null,
primary key(id)
);

-- customer
create table kame.customer(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
company_name varchar(64) not null,
pic_name varchar(64),
email_address varchar(256),
telephon_number varchar(11),
fax_number varchar(11),
address varchar(128),
building varchar(64),
primary key(id)
);

-- application
create table kame.application(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
name varchar(128) not null,
api_key varchar(64) not null,
api_secret varbinary(128) not null,
primary key(id),
unique(api_key)
);

-- file
create table kame.file(
id bigint auto_increment,
inserted varchar(256) not null,
insert_datetime timestamp not null,
updated varchar(256),
update_datetime timestamp,
update_seq integer not null default 0,
application_id bigint not null,
path varchar(512) not null,
content_type varchar(128),
data blob not null,
primary key(id),
unique(application_id, path)
);

