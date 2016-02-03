/*
drop table message;
drop table contact;
drop table register;
*/

create table if not exists register (
clientId text primary key,
name text not null,
currentIp text
);

create table if not exists contact (
contactId integer primary key autoincrement,
clientId not null,
firstName text,
lastName text,
name text not null,
mobile text unique,
email text unique,
foreign key(clientId) references register(clientId)
);

create table if not exists message (
messageId integer primary key autoincrement,
clientId not null,
contactId integer not null,
message text not null,
foreign key(contactId) references contact(contactId),
foreign key(clientId) references register(clientId)
);
