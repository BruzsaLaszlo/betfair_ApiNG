create table raw_navigation_data
(
    db_id             bigint auto_increment
        primary key,
    country_code      varchar(255) null,
    id                varchar(255) null,
    market_start_time datetime(6)  null,
    market_type       varchar(255) null,
    name              varchar(255) null,
    number_of_winners varchar(255) null,
    race_number       varchar(255) null,
    start_time        datetime(6)  null,
    type              varchar(255) null,
    venue             varchar(255) null,
    parent_id         bigint       null,
    constraint fk_raw_navigation_data
        foreign key (parent_id) references raw_navigation_data (db_id)
);