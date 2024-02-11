drop table if exists admin;
drop table if exists job;

create table if not exists admin (
    admin_id bigint not null primary key auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null
) engine=InnoDB default charset=utf8 collate=utf8_general_ci;

create table if not exists job (
    job_id bigint not null primary key auto_increment,
    title varchar(255) not null,
    url varchar(255) not null,
    company varchar(255) not null,
    notice_end_date varchar(255) not null,
    job_group varchar(255) not null,
    `active` boolean not null,
    is_push_sent boolean not null,
    created_at datetime not null,
    updated_at datetime not null
) engine=InnoDB default charset=utf8 collate=utf8_general_ci;
