use COMUNS_RELATIONAL;

create table EAGER (

ID integer not null auto_increment,
`VALUE` varchar(5) not null,

primary key (ID)) ENGINE=InnoDB;

create table EAGER_MANY (

ID integer not null auto_increment,
MANY varchar(5) not null,
EAGER_ID integer null,

primary key (ID)) ENGINE=InnoDB;