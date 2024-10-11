DELETE FROM honey_tip_like;
DELETE FROM honey_tip_image;
DELETE FROM honey_tip_url;
DELETE FROM honey_tip;
DELETE FROM member;

-- Member 생성
INSERT INTO member (id, origin_name, email, password, provider, nickname, street, independent_year, independent_month, fcm_token, role, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES (2000, 'testUser', 'test@example.com', 'password', 'origin', 'tester', '서울특별시 강남구', 5, 3, 'fcm_token_value', 'CLIENT', false, 'testUser', 'testUser', '2023-01-01 09:00:00', '2023-01-01 09:00:00');

-- HoneyTip 생성
INSERT INTO honey_tip (id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES (3000, '테스트 허니팁', '허니팁 내용입니다.', 'HOUSEWORK', 2000, false, 'testUser', 'testUser', '2023-02-01 09:00:00', '2023-02-01 09:00:00');

-- HoneyTipLike 생성
INSERT INTO honey_tip_like (id, member_id, honey_tip_id, created_date, last_modified_date)
VALUES (4000, 2000, 3000, '2023-02-01 09:30:00', '2023-02-01 09:30:00');
