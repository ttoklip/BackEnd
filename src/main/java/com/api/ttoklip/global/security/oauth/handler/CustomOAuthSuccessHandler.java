package com.api.ttoklip.global.security.oauth.handler;


import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.security.oauth.principal.CustomOAuth2User;
import com.api.ttoklip.global.security.oauth.response.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        Member member = oauth2User.getMember();
        boolean isFirstLogin = oauth2User.isFirstLogin();

        log.info("------------------ 소셜 로그인 성공: " + member.getName());

        String nickname = member.getName();
        Long memberId = member.getId();

        Member loginMember = memberService.findById(memberId);
        String profileImgUrl = loginMember.getProfile().getProfileImgUrl();
        String token = generateToken(loginMember.getId());

        AuthResponse authResponse = AuthResponse.builder()
                .memberId(loginMember.getId())
                .name(nickname)
                .token(token)
                .profileImageUrl(profileImgUrl)
                .isFirstLogin(isFirstLogin)
                .build();

        // 응답 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // JSON 형식으로 데이터 작성 및 전송
        objectMapper.writeValue(response.getWriter(), authResponse);

    }

    private String generateToken(final Long memberId) {
        String ourToken = jwtProvider.generateJwtToken(memberId);
        log.info("ourToken = " + ourToken);
        return ourToken;
    }

}
