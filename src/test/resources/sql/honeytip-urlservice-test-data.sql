DELETE FROM honey_tip_url;
DELETE FROM honey_tip_image;
DELETE FROM comment;
DELETE FROM honey_tip;
DELETE FROM MEMBER;

-- Member 생성 (필수적인 경우 추가)
INSERT INTO member (id, origin_name, email, password, provider, nickname, street, independent_year, independent_month, fcm_token, role, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES
    (1, '테스트유저', 'test@example.com', 'password123', 'origin', '테스터', '서울특별시 강남구', 5, 3, 'fcm_token_value', 'CLIENT', false, '테스트유저', '테스트유저', '2023-01-01 09:00:00', '2023-01-01 09:00:00');

-- HoneyTip 생성
INSERT INTO honey_tip (id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES
    (1, '테스트 허니팁', '허니팁 내용입니다.', 'HOUSEWORK', 1, false, '테스트유저', '테스트유저', '2023-02-01 09:00:00', '2023-02-01 09:00:00');

-- HoneyTipUrl 생성
INSERT INTO honey_tip_url (id, url, honey_tip_id, deleted, created_by, last_modified_by, created_date, last_modified_date)
VALUES
    (1, 'http://example.com/1', 1, false, '테스트유저', '테스트유저', '2023-02-01 09:30:00', '2023-02-01 09:30:00'),
    (2, 'http://example.com/2', 1, false, '테스트유저', '테스트유저', '2023-02-01 09:30:00', '2023-02-01 09:30:00');
