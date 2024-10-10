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
      (900, '제목', '내용', 'HOUSEWORK', 3001, false, '테스트유저', '테스트유저', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
