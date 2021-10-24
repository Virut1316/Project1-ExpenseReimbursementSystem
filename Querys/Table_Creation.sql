create table if not exists Reimbursement_status (
	reimb_status_id			int primary key generated always as identity,
	reimb_status			varchar(10) not null
);
create table if not exists Reimbursement_type (
	reimb_type_id			int primary key generated always as identity,
	reimb_type				varchar(10) not null
);
drop table if exists user_roles ;
create table if not exists User_roles (
	user_role_id			int primary key generated always as identity,
	user_role				varchar(10) not null
);

create table if not exists Users (
	users_id				int primary key generated always as identity,
	username				varchar(50) unique not null,
	password				varchar(50) not null,
	user_first_name			varchar(100) not null,
	user_last_name			varchar(100) not null,
	user_email				varchar(50) unique not null,
	user_role_id			int not null references User_roles(user_role_id)
);

create table if not exists Reimbursement (
	reimb_id				int primary key generated always as identity,
	reimb_amount			int not null,
	reimb_submitted			timestamp not null,
	reimb_resolved			timestamp,
	reimb_description		varchar(250),
	reimb_receipt			bytea,
	reimb_author			int not null references Users(users_id),
	reimb_resolver			int references Users(users_id),
	reimb_status_id			int not null references Reimbursement_status(reimb_status_id),
	reimb_type_id			int not null references Reimbursement_type(reimb_type_id)
);