/*
drop table message;
drop table contact;
drop table register;
*/

create table if not exists register (
clientId text primary key,
name text not null,
currentIp text,
lastRegistered datetime not null default CURRENT_TIMESTAMP
);

create table if not exists contact (
contactId integer primary key autoincrement,
clientId not null,
firstName text,
lastName text,
name text not null,
mobile text unique,
email text unique,
dateAdded datetime not null default CURRENT_TIMESTAMP,

foreign key(clientId) references register(clientId)
);

create table if not exists message (
messageId integer primary key autoincrement,
toClientId not null,
fromClientId not null,
contactId integer not null,
message text not null,
dateAdded datetime not null default CURRENT_TIMESTAMP,

foreign key(contactId) references contact(contactId),
foreign key(toClientId) references register(clientId),
foreign key(fromClientId) references register(clientId)
);
