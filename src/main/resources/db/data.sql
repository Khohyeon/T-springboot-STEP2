insert into user_tb(username, password, email, created_at) values('ssar', '1234', 'ssar@nate.com', now());


insert into board_tb(title, content, user_id, created_at) values('첫 번째 글입니다.','첫 번째 내용입니다.', 1, now()); 


commit;

