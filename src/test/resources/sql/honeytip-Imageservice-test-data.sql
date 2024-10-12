-- 데이터 정리
DELETE FROM honey_tip_image;
DELETE FROM comment;
DELETE FROM honey_tip;
DELETE FROM member;

-- Member 생성
INSERT INTO member (id, origin_name, email, password, provider, nickname, street, independent_year, independent_month, fcm_token, role, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES (2000, 'testUser', 'test@example.com', 'password', 'origin', 'tester', '서울특별시 강남구', 5, 3, 'fcm_token_value', 'CLIENT', false, 'testUser', 'testUser', '2023-01-01 09:00:00', '2023-01-01 09:00:00');

INSERT INTO member (id, origin_name, email, password, provider, nickname, street, independent_year, independent_month, fcm_token, role, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES (2001, 'otherUser', 'other@example.com', 'password', 'origin', 'otherTester', '서울특별시 강북구', 6, 4, 'other_fcm_token_value', 'CLIENT', false, 'otherUser', 'otherUser', '2023-01-01 09:00:00', '2023-01-01 09:00:00');

-- HoneyTip 생성 (testUser가 작성한 허니팁)
INSERT INTO honey_tip (id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES (3000, '테스트 허니팁', '허니팁 내용입니다.', 'HOUSEWORK', 2000, false, 'testUser', 'testUser', '2023-02-01 09:00:00', '2023-02-01 09:00:00');

-- HoneyTipImage 생성
INSERT INTO honey_tip_image (id, honey_tip_id, url, created_date, last_modified_date, deleted)
VALUES (4000, 3000, 'http://image-server.com/test-image1.jpg', '2023-02-01 09:30:00', '2023-02-01 09:30:00', false);

INSERT INTO honey_tip_image (id, honey_tip_id, url, created_date, last_modified_date, deleted)
VALUES (4001, 3000, 'http://image-server.com/test-image2.jpg', '2023-02-01 09:35:00', '2023-02-01 09:35:00', false);
