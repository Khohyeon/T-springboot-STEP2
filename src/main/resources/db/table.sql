create table user_tb(
    id int auto_increment primary key,
    username varchar not null unique,
    password varchar not null,
    email varchar not null,
    profile varchar,
    role varchar default USER, 
    created_at timestamp 
);
create table board_tb(
    id int auto_increment primary key,
    title varchar not null,
    content longtext not null,
    thumbnail longtext not null,
    user_id int,
    created_at timestamp
);
create table reply_tb(
    id int auto_increment primary key,
    comment varchar(100) not null,
    user_id int not null,
    board_id int not null,
    created_at timestamp
);
create table like_tb(
	id int AUTO_INCREMENT PRIMARY KEY not null,
    board_id int,
    user_id int,
    like_num int
);
