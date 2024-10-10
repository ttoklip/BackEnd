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

-- 7. RECIPE 카테고리 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (4001, '요리 팁 1', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4002, '요리 팁 2', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4003, '요리 팁 3', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4004, '요리 팁 4', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4005, '요리 팁 5', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4006, '요리 팁 6', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4007, '요리 팁 7', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4008, '요리 팁 8', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4009, '요리 팁 9', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4010, '요리 팁 10', '요리할 때 유용한 팁입니다.', 'RECIPE', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 8. SAFE_LIVING 카테고리 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (5001, '안전 생활 팁 1', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5002, '안전 생활 팁 2', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5003, '안전 생활 팁 3', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5004, '안전 생활 팁 4', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5005, '안전 생활 팁 5', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5006, '안전 생활 팁 6', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5007, '안전 생활 팁 7', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5008, '안전 생활 팁 8', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5009, '안전 생활 팁 9', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5010, '안전 생활 팁 10', '안전하게 생활할 수 있는 팁입니다.', 'SAFE_LIVING', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 9. WELFARE_POLICY 카테고리 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
      (6001, '복지 정책 팁 1', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6002, '복지 정책 팁 2', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6003, '복지 정책 팁 3', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6004, '복지 정책 팁 4', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6005, '복지 정책 팁 5', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6006, '복지 정책 팁 6', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6007, '복지 정책 팁 7', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6008, '복지 정책 팁 8', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6009, '복지 정책 팁 9', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6010, '복지 정책 팁 10', '복지 정책 관련 팁입니다.', 'WELFARE_POLICY', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);