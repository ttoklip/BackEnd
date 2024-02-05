package com.api.ttoklip.domain.auth.service;

import com.api.ttoklip.domain.auth.converter.AuthConverter;
import com.api.ttoklip.domain.auth.dto.request.SignInReq;
import com.api.ttoklip.domain.auth.dto.request.SignUpReq;
import com.api.ttoklip.domain.auth.dto.response.AuthRes;
import com.api.ttoklip.domain.auth.dto.response.TokenMapping;
import com.api.ttoklip.domain.common.base.Status;
import com.api.ttoklip.domain.user.domain.Role;
import com.api.ttoklip.domain.user.domain.User;
import com.api.ttoklip.domain.user.domain.repository.UserRepository;
import com.api.ttoklip.global.DefaultAssert;
import com.api.ttoklip.global.config.security.token.UserPrincipal;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomTokenProviderService customTokenProviderService;

    private final UserRepository userRepository;


    @Transactional
    public Long signUp(SignUpReq signUpReq) {

        User user = AuthConverter.toUser(signUpReq);
        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public AuthRes signIn(SignInReq signInReq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInReq.getEmail(),
                        signInReq.getProviderId()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);

        AuthRes authRes = new AuthRes(tokenMapping.getAccessToken());
        return authRes;
    }

    @Transactional
    public Message signOut(UserPrincipal userPrincipal) {
        return null;
    }

    @Transactional
    public Message checkNickname(String nickname) {
        Optional<User> byNickname = userRepository.findByNickname(nickname);
        if (byNickname.isPresent()) {
            return new Message("닉네임이 이미 사용중입니다.");
        }
        return new Message("닉네임 사용 가능합니다.");
    }
}
