package com.api.ttoklip.domain.auth.service;

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

        Optional<User> existingUser = userRepository.findByProviderId(signUpReq.getProviderId());
        String imageUrl = existingUser.map(User::getImageUrl).orElse(signUpReq.getImageUrl());

        User user = existingUser.get();
        user.updateImageUrl(imageUrl);
        user.updateNickname(signUpReq.getNickname());
        // 기타 필요한 정보 업데이트
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Message checkNickname(String nickname) {
        Optional<User> byNickname = userRepository.findByNickname(nickname);
        if (byNickname.isPresent()) {
            return Message.nicknameInUse();
        }
        return Message.nicknameAvailable();
    }

    public SuccessResponse<?> whoAmI(UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(user);
        return new SuccessResponse(user);
    }
}
