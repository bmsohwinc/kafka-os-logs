create database if not exists kos;

create table if not exists os_service_log (
    log_date_time datetime,
    host_name varchar(40),
    process_name varchar(40),
    process_id varchar(40),
    service_name varchar(100)
);
