package com.api.ttoklip.domain.auth.service;

import com.api.ttoklip.domain.auth.dto.AuthRes;
import com.api.ttoklip.domain.auth.dto.KakaoProfile;
import com.api.ttoklip.domain.auth.dto.OAuthToken;
import com.api.ttoklip.domain.auth.dto.TokenMapping;
import com.api.ttoklip.domain.user.domain.Provider;
import com.api.ttoklip.domain.user.domain.Role;
import com.api.ttoklip.domain.user.domain.User;
import com.api.ttoklip.domain.user.domain.repository.UserRepository;
import com.api.ttoklip.global.DefaultAssert;
import com.api.ttoklip.global.config.security.OAuth2Config;
import com.api.ttoklip.global.config.security.token.UserPrincipal;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    @Autowired
    private OAuth2Config oAuth2Config;

    private final AuthenticationManager authenticationManager;

    private final RestTemplate rt;
    private final ObjectMapper objectMapper;
    private final HttpServletResponse response;

    private final UserRepository userRepository;
//    private final TokenRepository tokenRepository;
    private final CustomTokenProviderService customTokenProviderService;


    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String KAKAO_SNS_URL;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_Uri;


    @Transactional
    public String getKakaoAccessToken(String code) {

        String access_Token = "";
        String refresh_Token = "";

        // Post 요청 라이브러리
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // http 바디 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_SNS_CLIENT_ID);
        params.add("redirect_uri", redirect_Uri);
        params.add("code", code);

        // httpHeader와 httpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // 실제 요청 Http post 방식 그리고 response 변수에 응답 받는다
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        access_Token = oauthToken.getAccess_token();

        return access_Token;

    }


    @Transactional
    public void accessRequest() throws IOException {

        Map<String, Object> params = new HashMap<>();
//        params.put("scope", "email");
        params.put("response_type", "code");
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", redirect_Uri);


        //parameter를 형식에 맞춰 구성해주는 함수
        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL = KAKAO_SNS_URL + "?" + parameterString;
        log.info("redirectURL = ", redirectURL);

        response.sendRedirect(redirectURL);
    }

    @Transactional
    public KakaoProfile getKakaoProfile(String accessToken) {
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + accessToken);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );


        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return kakaoProfile;
    }


    @Transactional
    public AuthRes kakaoLogin(KakaoProfile kakaoProfile) {

        // 이미 DB에 회원 정보가 저장되어 있으면 로그인 시키고, 없다면 DB에 등록 후 로그인.

        Optional<User> byEmail = userRepository.findByEmail(kakaoProfile.getKakaoAccount().getEmail());
        if (!byEmail.isPresent()) {
            User user = User.builder()
                    .providerId(kakaoProfile.getId())
                    .provider(Provider.KAKAO)
                    .email(kakaoProfile.getKakaoAccount().getEmail())
                    .name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
                    .role(Role.USER)
                    .build();

            User saveUser = userRepository.save(user);


        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        kakaoProfile.getKakaoAccount().getEmail(),
                        kakaoProfile.getId() //providerId랑 같다.
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);


//        Token token = Token.builder()
//                .refreshToken(tokenMapping.getRefreshToken())
//                .email(tokenMapping.getEmail())
//                .build();
//
//        tokenRepository.save(token);

//        Token savedToken = tokenRepository.save(token);





        return AuthRes.builder()
                .accessToken(tokenMapping.getAccessToken())
//                .refreshToken(token.getRefreshToken())
                .role(Role.USER)
                .build();
    }


//    @Transactional
//    public Message signOut(final RefreshTokenReq tokenRefreshRequest) {
//        Token token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken())
//                .orElseThrow(() -> new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION));
//        tokenRepository.delete(token);
//
//        return Message.builder()
//                .message("로그아웃 하였습니다.")
//                .build();
//    }



    public SuccessResponse<?> whoAmI(UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(user);
        return null;
    }

}
