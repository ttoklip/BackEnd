package com.api.ttoklip.global.security.auth.service;

import com.api.ttoklip.global.security.auth.dto.request.AuthRequest;
import com.api.ttoklip.global.security.auth.dto.request.AuthLoginRequest;
import com.api.ttoklip.global.security.auth.dto.response.AuthLoginResponse;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.oauth2.service.OAuthService;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.success.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final OAuthService OAuthService;
    private static final String PROVIDER_LOCAL = "local";

    @Transactional
    public Message signup(final AuthRequest authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        String originName = authRequest.getOriginName();

        boolean isExist = memberRepository.existsByEmail(email);

        if (isExist) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_JOINID);
        }

        String registerPassword = bCryptPasswordEncoder.encode(password);
        log.info("---------------------" + registerPassword);
        Member data = Member.builder()
                .email(email)
                .password(registerPassword)
                .originName(originName)
                .role(Role.CLIENT)
                .provider(PROVIDER_LOCAL)
                .build();

        memberRepository.save(data);

        return Message.registerUser();
    }

    @Transactional
    public Message duplicate(final String email) {
        boolean isExist = memberRepository.existsByEmail(email);
        if (isExist) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_JOINID);
        }
        return Message.validId();
    }

    public AuthLoginResponse login(final AuthLoginRequest authLoginRequest) {

        Member loginMember = authenticate(authLoginRequest);
        String jwtToken = jwtProvider.generateJwtToken(loginMember.getEmail());

        boolean existsNickname = memberService.isExistsNickname(loginMember.getNickname());
        if (existsNickname) {
            return getLoginResponse(jwtToken, false);
        }

        return getLoginResponse(jwtToken, true);
    }

    private Member authenticate(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.getEmail();
        String password = authLoginRequest.getPassword();

        Member findMember = memberService.findByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, findMember.getPassword())) {
            throw new ApiException(ErrorType.AUTH_INVALID_PASSWORD);
        }

        return findMember;
    }

    private AuthLoginResponse getLoginResponse(final String jwtToken, final boolean ifFirstLogin) {
        // Server JWT Token
        return AuthLoginResponse.builder()
                .jwtToken(jwtToken)
                .ifFirstLogin(ifFirstLogin)
                .build();
    }

}

