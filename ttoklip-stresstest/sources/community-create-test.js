import http from 'k6/http';
import { check, sleep } from 'k6';

// 테스트 옵션 설정
export const options = {
  vus: 10000,          // 동시 사용자 수
  iterations: 10000,   // 총 요청 수
};

// 게시글 생성 데이터를 만드는 함수
function generatePostData(index) {
    const uniqueKey = `${index}-${Date.now()}-${Math.random().toString(36).substring(2, 8)}`;
    return {
      title: `테스트 제목 ${uniqueKey}`,
      content: `테스트 내용 ${uniqueKey}`,
      images: http.file('./test-image.png', 'test-image.png')  // 파일 경로 확인
    };
}

export default function () {
  const userIndex = __VU;  // Virtual User ID
  const postData = generatePostData(userIndex);

  const url = 'https://toychip.click/api/v1/town/comms';

  // ✅ Content-Type 삭제 (자동으로 boundary 추가됨)
  const headers = {
    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MS0xNzM2NDE1Mzc5NjMzLWcxb3ljakBleGFtcGxlLmNvbSIsImlhdCI6MTczNjUxMDcxMiwiZXhwIjoxNzM5MTAyNzEyfQ.3EJbFFo-Jfh6KfszN81OlXqom9zTp-Lf511tgvFDZSU'
  };

  // Multipart form data 생성
  const formData = {
    title: postData.title,
    content: postData.content,
    images: postData.images
  };

  // POST 요청 전송
  const res = http.post(url, formData, { headers: headers });

  // 응답 확인
  check(res, {
    'status is 201': (r) => r.status === 201,
    'response body is not empty': (r) => r.body && r.body.length > 0,
  });

  sleep(10);  // 사용자별 대기 시간
}
