create table node_event_types
(
    id   varchar(255) not null
        primary key,
    name varchar(255) null
);

create table node_events
(
    id            varchar(255) not null
        primary key,
    name          varchar(255) null,
    country_code  varchar(255) null,
    group_id      varchar(255) null,
    event_type_id varchar(255) null,
    event_id      varchar(255) null
);

create table node_groups
(
    id            varchar(255) not null
        primary key,
    name          varchar(255) null,
    group_id      varchar(255) null,
    event_type_id varchar(255) null,
    event_id      varchar(255) null
);

create table node_markets
(
    id                varchar(255) not null
        primary key,
    name              varchar(255) null,
    market_start_time datetime(6)  null,
    market_type       varchar(255) null,
    number_of_winners varchar(255) null,
    race_id           varchar(255) null,
    event_id          varchar(255) null
);

create table node_races
(
    id            varchar(255) not null
        primary key,
    name          varchar(255) null,
    country_code  varchar(255) null,
    race_number   varchar(255) null,
    start_time    datetime(6)  null,
    venue         varchar(255) null,
    event_type_id varchar(255) null
);
