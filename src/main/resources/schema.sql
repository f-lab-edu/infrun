DROP TABLE IF EXISTS lecture;
create table lecture
(
    id bigint generated by default as identity,
    name varchar(30),
    price integer,
    lecture_level varchar(30),
    skill varchar(30),
    introduce varchar(200),
    created_at timestamp,
    modified_at timestamp,
    user_name varchar(20),
    user_id bigint,
    primary key (id)
);

DROP TABLE IF EXISTS lecture_detail;
create table lecture_detail
(
    id bigint generated by default as identity,
    chapter varchar(30),
    name varchar(30),
    lecture_id bigint,
    file_id bigint NULL,
    created_at timestamp,
    modified_at timestamp,
    chapter_id bigint,
    primary key (id)
);

DROP TABLE IF EXISTS file;
create table file
(
    id bigint generated by default as identity,
    url varchar(500),
    name varchar(30),
    created_at timestamp,
    modified_at timestamp,
    primary key (id)
);