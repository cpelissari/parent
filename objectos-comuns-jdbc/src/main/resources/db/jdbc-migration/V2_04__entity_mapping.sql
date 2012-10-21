use COMUNS_RELATIONAL;

drop table if exists MAPPING;

create table MAPPING (

ID int(11) not null auto_increment,
BOOLEAN_VALUE tinyint(1) null,
INT_VALUE int(11) null,
LONG_VALUE bigint null,
FLOAT_VALUE float null,
DOUBLE_VALUE double null,
BIG_DECIMAL decimal(10,2) null,
JAVA_DATE datetime null,
LOCAL_DATE date null,
DATE_TIME datetime null,
TEXT varchar(140) null,

primary key (id)

) engine=InnoDB default charset=utf8;