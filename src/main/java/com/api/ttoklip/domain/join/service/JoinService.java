package com.api.ttoklip.domain.join.service;

import com.api.ttoklip.domain.join.dto.request.JoinRequest;
import com.api.ttoklip.domain.join.dto.request.LoginRequest;
import com.api.ttoklip.domain.join.dto.response.LoginResponse;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.auth.service.AuthService;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.success.Message;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Transactional
    public Message signup(JoinRequest joinRequest) {

        String email = joinRequest.getEmail();
        String password = joinRequest.getPassword();
        String originName = joinRequest.getOriginName();
        String birth = joinRequest.getBirth();

        Boolean isExist = memberRepository.existsByEmail(email);

        if (isExist) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_JOINID);
        }
        Member data = Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .originName(originName)
//                .birth
                .role(Role.CLIENT)
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

    public LoginResponse login(final LoginRequest loginRequest) {


        if (!localoginSuccess(loginRequest)) {
            throw new ApiException(ErrorType._LOGIN_FAIL);
        }

        String email = loginRequest.getEmail();
        String jwtToken = jwtProvider.generateJwtToken(email);

//        Member findMember = memberService.findByEmail(email);
        Member findMember = memberService.findByEmail(email);
        jwtProvider.setContextHolder(jwtToken, findMember);

        boolean existsNickname = memberService.isExistsNickname(findMember.getNickname());
        if (existsNickname) {
            return getLoginResponse(jwtToken, false);
        }

        return getLoginResponse(jwtToken, true);
    }

    private boolean localoginSuccess(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Member findMember = memberService.findByEmail(email);

//        boolean success = findMember.getPassword().equals(password);
        boolean success = findMember.getEmail().equals(email);
        if (!success) {
            return false;
        }

        return true;
    }

    private LoginResponse getLoginResponse(final String jwtToken, final boolean ifFirstLogin) {
        // Server JWT Token
        return LoginResponse.builder()
                .jwtToken(jwtToken)
                .ifFirstLogin(ifFirstLogin)
                .build();
    }

}

