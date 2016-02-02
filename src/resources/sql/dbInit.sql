create table if not exists register (
uuid text primary key,
name text not null,
currentIp text
);

create table if not exists contact (
contactId integer primary key autoincrement,
registerUuid not null,
firstName text,
lastName text,
name text not null,
mobile integer unique,
email text unique,
foreign key(registerUuid) references register(uuid)
);

create table if not exists message (
messageId integer primary key autoincrement,
registerUuid not null,
contactId integer not null,
message text not null,
foreign key(contactId) references contact(contactId),
foreign key(registerUuid) references register(uuid)
);
