drop table if exists attendance;
drop table if exists role;
drop table if exists account_team;
drop table if exists account;
drop table if exists team;
drop table if exists event;
drop table if exists match;
drop table if exists file;

create table account (
    id bigint primary key auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255) unique,
    phone varchar(255),
    birthday date
);

create table team (
    id bigint primary key auto_increment,
    name varchar(255),
    city varchar(255),
    sport_type varchar(255)
);

create table account_team (
    account_id bigint not null,
    team_id bigint not null,
    primary key (account_id, team_id),
    foreign key (account_id) references account(id),
    foreign key (team_id) references team(id)
);

create table role (
    id bigint primary key auto_increment,
    role_type varchar(255),
    account_id bigint,
    foreign key (account_id) references account(id)
);

create table event (
    id bigint primary key auto_increment,
    name varchar(255),
    start_date date,
    start_time time,
    city varchar(255),
    address varchar(255),
    event_type varchar(255),
    team_id bigint,
    foreign key (team_id) references team(id)
);

create table match (
    id bigint primary key auto_increment,
    start_date date,
    start_time time,
    city varchar(255),
    address varchar(255),
    team_score int,
    opponent_name varchar(255),
    opponent_phone varchar(255),
    opponent_score int,
    team_id bigint,
    foreign key (team_id) references team(id)
);

create table attendance (
    id bigint primary key auto_increment,
    status_type varchar(255),
    account_id bigint,
    event_id bigint,
    match_id bigint,
    foreign key (account_id) references account(id),
    foreign key (event_id) references event(id),
    foreign key (match_id) references match(id)
);

create table file (
    id bigint primary key auto_increment,
    name varchar(255),
    upload_date date,
    type varchar(31)
);
-- document and image use file table with type discriminator
