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
     3, 'fcm_token_value', 'CLIENT', false, '테스트유저', '테스트유저', '2023-01-01 09:00:00', '2023-01-01 09:00:00');

-- 3. 기본 조회 테스트 및 findHouseworkTips 테스트를 위한 HoneyTip 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (900, '제목', '내용', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-01 09:00:00', '2023-02-01 09:00:00'),
      (3101, '청소 팁 1', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-02 09:00:00', '2023-02-02 09:00:00'),
      (3102, '청소 팁 2', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-03 09:00:00', '2023-02-03 09:00:00'),
      (3103, '청소 팁 3', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-04 09:00:00', '2023-02-04 09:00:00'),
      (3104, '청소 팁 4', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-05 09:00:00', '2023-02-05 09:00:00'),
      (3105, '청소 팁 5', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-06 09:00:00', '2023-02-06 09:00:00'),
      (3106, '청소 팁 6', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-07 09:00:00', '2023-02-07 09:00:00'),
      (3107, '청소 팁 7', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-08 09:00:00', '2023-02-08 09:00:00'),
      (3108, '청소 팁 8', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-09 09:00:00', '2023-02-09 09:00:00'),
      (3109, '청소 팁 9', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-10 09:00:00', '2023-02-10 09:00:00'),
      (3110, '청소 팁 10', '청소할 때 유용한 팁입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-02-11 09:00:00', '2023-02-11 09:00:00');

-- 4. findHoneyTipWithDetails 테스트를 위한 HoneyTip 데이터 (이미지와 URL 정보가 필요함)
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
    (3200, '세부 정보 테스트', '세부 정보를 확인하는 테스트입니다.', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', '2023-03-01 09:00:00', '2023-03-01 09:00:00');

-- 5. HoneyTipImage 테이블에 데이터 삽입 (findHoneyTipWithDetails 테스트용)
INSERT INTO honey_tip_image (
    id, url, honey_tip_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (3301, 'https://example.com/image1.jpg', 3200, false, '테스트유저', '테스트유저', '2023-03-01 09:00:00', '2023-03-01 09:00:00'),
      (3302, 'https://example.com/image2.jpg', 3200, false, '테스트유저', '테스트유저', '2023-03-01 09:00:00', '2023-03-01 09:00:00');

-- 6. HoneyTipUrl 테이블에 데이터 삽입 (findHoneyTipWithDetails 테스트용)
INSERT INTO honey_tip_url (
    id, url, honey_tip_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (3401, 'https://example.com', 3200, false, '테스트유저', '테스트유저', '2023-03-01 09:00:00', '2023-03-01 09:00:00'),
      (3402, 'https://another-example.com', 3200, false, '테스트유저', '테스트유저', '2023-03-01 09:00:00', '2023-03-01 09:00:00');

-- 7. RECIPE 카테고리 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (4001, '요리 팁 1', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-01 09:00:00', '2023-04-01 09:00:00'),
      (4002, '요리 팁 2', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-02 09:00:00', '2023-04-02 09:00:00'),
      (4003, '요리 팁 3', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-03 09:00:00', '2023-04-03 09:00:00'),
      (4004, '요리 팁 4', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-04 09:00:00', '2023-04-04 09:00:00'),
      (4005, '요리 팁 5', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-05 09:00:00', '2023-04-05 09:00:00'),
      (4006, '요리 팁 6', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-06 09:00:00', '2023-04-06 09:00:00'),
      (4007, '요리 팁 7', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-07 09:00:00', '2023-04-07 09:00:00'),
      (4008, '요리 팁 8', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-08 09:00:00', '2023-04-08 09:00:00'),
      (4009, '요리 팁 9', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-09 09:00:00', '2023-04-09 09:00:00'),
      (4010, '요리 팁 10', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', '2023-04-10 09:00:00', '2023-04-10 09:00:00');

-- 8. SAFE_LIVING 카테고리 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (5001, '안전 생활 팁 1', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-01 09:00:00', '2023-05-01 09:00:00'),
      (5002, '안전 생활 팁 2', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-02 09:00:00', '2023-05-02 09:00:00'),
      (5003, '안전 생활 팁 3', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-03 09:00:00', '2023-05-03 09:00:00'),
      (5004, '안전 생활 팁 4', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-04 09:00:00', '2023-05-04 09:00:00'),
      (5005, '안전 생활 팁 5', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-05 09:00:00', '2023-05-05 09:00:00'),
      (5006, '안전 생활 팁 6', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-06 09:00:00', '2023-05-06 09:00:00'),
      (5007, '안전 생활 팁 7', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-07 09:00:00', '2023-05-07 09:00:00'),
      (5008, '안전 생활 팁 8', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-08 09:00:00', '2023-05-08 09:00:00'),
      (5009, '안전 생활 팁 9', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-09 09:00:00', '2023-05-09 09:00:00'),
      (5010, '안전 생활 팁 10', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', '2023-05-10 09:00:00', '2023-05-10 09:00:00');

-- 9. WELFARE_POLICY 카테고리 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (6001, '복지 정책 팁 1', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-01 09:00:00', '2023-06-01 09:00:00'),
      (6002, '복지 정책 팁 2', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-02 09:00:00', '2023-06-02 09:00:00'),
      (6003, '복지 정책 팁 3', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-03 09:00:00', '2023-06-03 09:00:00'),
      (6004, '복지 정책 팁 4', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-04 09:00:00', '2023-06-04 09:00:00'),
      (6005, '복지 정책 팁 5', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-05 09:00:00', '2023-06-05 09:00:00'),
      (6006, '복지 정책 팁 6', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-06 09:00:00', '2023-06-06 09:00:00'),
      (6007, '복지 정책 팁 7', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-07 09:00:00', '2023-06-07 09:00:00'),
      (6008, '복지 정책 팁 8', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-08 09:00:00', '2023-06-08 09:00:00'),
      (6009, '복지 정책 팁 9', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-09 09:00:00', '2023-06-09 09:00:00'),
      (6010, '복지 정책 팁 10', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', '2023-06-10 09:00:00', '2023-06-10 09:00:00');
