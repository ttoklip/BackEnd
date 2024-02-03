package com.api.ttoklip.domain.auth.service;

import com.api.ttoklip.domain.auth.dto.AuthRes;
import com.api.ttoklip.domain.auth.dto.NaverProfile;
import com.api.ttoklip.domain.auth.dto.TokenMapping;
import com.api.ttoklip.domain.user.domain.Provider;
import com.api.ttoklip.domain.user.domain.Role;
import com.api.ttoklip.domain.user.domain.User;
import com.api.ttoklip.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverService {

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String NAVER_SNS_URL;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_SNS_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_SNS_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirect_Uri;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String token_uri;


    private final AuthenticationManager authenticationManager;

    private final CustomTokenProviderService customTokenProviderService;

    private final UserRepository userRepository;


    @Transactional
    public String getNaverAuthorizeUrl() throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(NAVER_SNS_URL)
                .queryParam("response_type", "code")
                .queryParam("client_id", NAVER_SNS_CLIENT_ID)
                .queryParam("redirect_uri", redirect_Uri)
                .queryParam("state", URLEncoder.encode("1234", "UTF-8"))
                .build();

        return uriComponents.toString();
    }

    @Transactional
    public JSONObject getNaverToken(String code, String state) {
        RestTemplate rt = new RestTemplate();

        String uriWithParams = UriComponentsBuilder.fromUriString(token_uri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", NAVER_SNS_CLIENT_ID)
                .queryParam("client_secret", NAVER_SNS_CLIENT_SECRET)
                .queryParam("code", code)
                .queryParam("state", state)
                .toUriString();

        String response = rt.getForObject(uriWithParams, String.class);

        JSONObject jsonObject = new JSONObject(response);
        return jsonObject;
    }

    @Transactional
    public NaverProfile getNaverUserInfo(String accessToken) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String userInfoUri = "https://openapi.naver.com/v1/nid/me"; // 네이버 유저 정보 API URI

        ResponseEntity<NaverProfile> response = rt.exchange(userInfoUri, HttpMethod.GET, entity, NaverProfile.class);

        System.out.println("response = " + response);
        System.out.println("response.getBody() = " + response.getBody());
        return response.getBody();
    }


    @Transactional
    public String getNaverAccessToken(String code, String state) {
        JSONObject tokenJson = getNaverToken(code, state);
        return tokenJson.getString("access_token");
    }

    @Transactional
    public AuthRes naverLogin(NaverProfile naverProfile) {
        System.out.println("naverProfile.getEmail() = " + naverProfile.getNaverResponse());

        // 이미 DB에 회원 정보가 저장되어 있으면 로그인 시키고, 없다면 DB에 등록 후 로그인.
        Optional<User> byEmail = userRepository.findByEmail(naverProfile.getNaverResponse().getEmail());
        if (!byEmail.isPresent()) {
            User user = User.builder()
                    .providerId(naverProfile.getNaverResponse().getId())
                    .provider(Provider.NAVER)
                    .email(naverProfile.getNaverResponse().getEmail())
                    .name(naverProfile.getNaverResponse().getName())
                    .role(Role.USER)
                    .build();

            User saveUser = userRepository.save(user);

        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        naverProfile.getNaverResponse().getEmail(),
                        naverProfile.getNaverResponse().getId() //providerId랑 같다.
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
}
