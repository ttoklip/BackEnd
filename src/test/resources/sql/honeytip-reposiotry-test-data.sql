-- 1. 데이터 초기화
DELETE FROM honey_tip_image;
DELETE FROM honey_tip_url;
DELETE FROM honey_tip;
DELETE FROM member;


-- 2. Member 테이블에 데이터 삽입 (먼저 실행해야 함)
INSERT INTO member (
    id, origin_name, email, password, provider, nickname, street, independent_year,
    independent_month, fcm_token, role, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
    (3001, '테스트유저', 'test@example.com', 'password123', 'origin', '테스터', '서울특별시 강남구', 5,
     3, 'fcm_token_value', 'CLIENT', false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. 기본 조회 테스트 및 findHouseworkTips 테스트를 위한 HoneyTip 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (900, '제목', '내용', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3101, '청소 팁 1', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3102, '청소 팁 2', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3103, '청소 팁 3', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3104, '청소 팁 4', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3105, '청소 팁 5', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3106, '청소 팁 6', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3107, '청소 팁 7', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3108, '청소 팁 8', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3109, '청소 팁 9', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3110, '청소 팁 10', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. findHoneyTipWithDetails 테스트를 위한 HoneyTip 데이터 (이미지와 URL 정보가 필요함)
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
    (3200, '세부 정보 테스트', '세부 정보를 확인하는 테스트입니다.', 'HOUSEWORK', 3001, FALSE, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. HoneyTipImage 테이블에 데이터 삽입 (findHoneyTipWithDetails 테스트용)
INSERT INTO honey_tip_image (
    id, url, honey_tip_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (3301, 'https://example.com/image1.jpg', 3200, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3302, 'https://example.com/image2.jpg', 3200, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 6. HoneyTipUrl 테이블에 데이터 삽입 (findHoneyTipWithDetails 테스트용)
INSERT INTO honey_tip_url (
    id, url, honey_tip_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (3401, 'https://example.com', 3200, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3402, 'https://another-example.com', 3200, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

