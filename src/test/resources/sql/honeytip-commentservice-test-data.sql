-- 1. 데이터 초기화
DELETE FROM honey_tip_url;
DELETE FROM honey_tip_image;
DELETE FROM comment;
DELETE FROM honey_tip;
DELETE FROM MEMBER;

-- 2. Member 테이블에 데이터 삽입
INSERT INTO member (
    id, origin_name, email, password, provider, nickname, street, independent_year,
    independent_month, fcm_token, role, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
    (1, '테스트유저', 'test@example.com', 'password123', 'origin', '테스터', '서울특별시 강남구', 5,
     3, 'fcm_token_value', 'CLIENT', false, '테스트유저', '테스트유저', '2023-01-01 09:00:00', '2023-01-01 09:00:00');

-- 3. HoneyTip 테이블에 데이터 삽입
INSERT INTO honey_tip (
    id, title, content, category, member_id, deleted, created_by, last_modified_by, created_date, last_modified_date
) VALUES
    (1, '테스트 허니팁', '허니팁 내용입니다.', 'HOUSEWORK', 1, false, '테스트유저', '테스트유저', '2023-02-01 09:00:00', '2023-02-01 09:00:00');

-- 4. HoneyTipComment 테이블에 데이터 삽입
INSERT INTO comment (
    id, content, member_id, honey_tip_id, parent_id, deleted, created_by, last_modified_by, created_date, last_modified_date, DTYPE
) VALUES
      (1, '첫 번째 댓글', 1, 1, null, false, '테스트유저', '테스트유저', '2023-02-02 10:00:00', '2023-02-02 10:00:00', 'HoneyTip'),
      (2, '두 번째 댓글', 1, 1, 1, false, '테스트유저', '테스트유저', '2023-02-02 11:00:00', '2023-02-02 11:00:00', 'HoneyTip'),
      (3, '세 번째 댓글', 1, 1, null, false, '테스트유저', '테스트유저', '2023-02-02 12:00:00', '2023-02-02 12:00:00', 'HoneyTip');
