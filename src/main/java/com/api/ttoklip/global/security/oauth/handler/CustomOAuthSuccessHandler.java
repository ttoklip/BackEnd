package com.api.ttoklip.global.security.oauth.handler;


import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.security.oauth.principal.CustomOAuth2User;
import com.api.ttoklip.global.security.oauth.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    public static final String REDIRECT_URL = "http://localhost:3000";
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        if (response.isCommitted()) {
            log.debug("------------------ Response 전송 완료");
        }

        Member member = oauth2User.getMember();

        log.info("------------------ 소셜 로그인 성공: " + member.getUserNickname());

        String email = member.getEmail();
        String nickname = member.getUserNickname();
        String profileImgUrl = member.getProfile().getProfileImgUrl();

        Member loginMember = memberService.findByEmail(email);

        String token = generateToken(loginMember.getEmail());

        AuthResponse authResponse = AuthResponse.builder()
                .memberId(loginMember.getId())
                .memberEmail(email)
                .nickname(nickname)
                .profileImageUrl(profileImgUrl)
                .build();

        // ToDo 아래는 임시 데이터, front와 협의 후 수정
        registerResponse(response, authResponse, token);
    }

    private String generateToken(final String loginMEmberEmail) {
        String ourToken = jwtProvider.generateJwtToken(loginMEmberEmail);
        log.info("ourToken = " + ourToken);
        return ourToken;
    }

    private void registerResponse(final HttpServletResponse response,
                                  final AuthResponse authResponse, String token) throws IOException {
        String encodedMemberId = URLEncoder.encode(String.valueOf(authResponse.memberId()), StandardCharsets.UTF_8);
        String encodedMemberNickname = URLEncoder.encode(authResponse.nickname(), StandardCharsets.UTF_8);
        String encodedProfileImageUrl = URLEncoder.encode(authResponse.profileImageUrl(), StandardCharsets.UTF_8);

        // 프론트엔드 페이지로 토큰과 함께 리다이렉트
        String frontendRedirectUrl = String.format(
                "%s/?token=%s&memberId=%s&gitLoginId=%s&profileImgUrl=%s",
                REDIRECT_URL, token, encodedMemberId, encodedMemberNickname, encodedProfileImageUrl
        );
        log.info("Front! redirect url: " + REDIRECT_URL);
        response.sendRedirect(frontendRedirectUrl);
    }

}
