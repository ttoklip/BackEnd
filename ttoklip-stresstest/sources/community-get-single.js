import http from 'k6/http';
import { check, sleep } from 'k6';

// ✅ 부하 테스트 옵션
export const options = {
  vus: 3000,           // 동시 사용자 수 (가상 사용자)
  iterations: 3000,    // 총 요청 수
};

// ✅ 테스트할 게시글 ID 배열 (임의의 postId 값)
const postIds = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];  // 실제 DB에 있는 게시글 ID로 수정 필요

// ✅ JWT 토큰 (인증용)
const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MS0xNzM2NDE1Mzc5NjMzLWcxb3ljakBleGFtcGxlLmNvbSIsImlhdCI6MTczNjUxMDcxMiwiZXhwIjoxNzM5MTAyNzEyfQ.3EJbFFo-Jfh6KfszN81OlXqom9zTp-Lf511tgvFDZSU';

// ✅ 요청 헤더
const headers = {
  'Authorization': token,
  'Content-Type': 'application/json'
};

// ✅ 부하 테스트 실행
export default function () {
  const randomIndex = Math.floor(Math.random() * postIds.length);  // 무작위 postId 선택
  const postId = postIds[randomIndex];
  const url = `https://toychip.click/api/v1/town/comms/${postId}`;  // 단건 조회 API

  // ✅ GET 요청 전송
  const res = http.get(url, { headers: headers });

  // ✅ 응답 체크
  check(res, {
    'Status is 200': (r) => r.status === 200,              // 응답 코드 200 확인
    'Response body is not empty': (r) => r.body.length > 0 // 응답 내용 확인
  });

  sleep(1);  // 1초 대기 후 다음 요청
}
