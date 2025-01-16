import http from 'k6/http';
import { check, sleep } from 'k6';

// 테스트 옵션 설정
export const options = {
  vus: 1000,
  iterations: 1000, // 테스트 횟수
};

function generateUser(index) {
    const uniqueKey = `${index}-${Date.now()}-${Math.random().toString(36).substring(2, 8)}`;
    return {
      email: `test${uniqueKey}@example.com`,
      password: '1234',
      originName: `Origin Name ${index}`,
      nickname: `nickname_${uniqueKey}`,
      independentYear: `${Math.floor(Math.random() * 5) + 1}`,
      independentMonth: `${Math.floor(Math.random() * 12) + 1}`,
      street: `서울특별시 동작구 노량진동`,
      agreeTermsOfService: 'true',
      agreePrivacyPolicy: 'true',
      agreeLocationService: 'true',
      categories: 'HOUSEWORK',
      profileImage: http.file('./profile.png', 'profile.png'),
    };
  }  

export default function () {
  const userIndex = __VU; // Virtual User ID
  const user = generateUser(userIndex);

  const url = 'https://toychip.click/api/v1/auth/signup';

  // Multipart form data 생성
  const formData = {
    email: user.email,
    password: user.password,
    originName: user.originName,
    nickname: user.nickname,
    independentYear: user.independentYear,
    independentMonth: user.independentMonth,
    street: user.street,
    agreeTermsOfService: user.agreeTermsOfService,
    agreePrivacyPolicy: user.agreePrivacyPolicy,
    agreeLocationService: user.agreeLocationService,
    categories: user.categories,
    profileImage: user.profileImage,
  };

  // Boundary는 K6가 자동으로 추가하므로 Content-Type만 지정
  const res = http.post(url, formData);

  // 응답 확인
  check(res, {
    'status is 201': (r) => r.status === 201,
    'response body is not empty': (r) => r.body && r.body.length > 0,
  });

  sleep(10); // 대기 시간
}
