package com.api.ttoklip.global.security.auth.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.privacy.domain.Interest;
import com.api.ttoklip.domain.privacy.domain.Profile;
import com.api.ttoklip.domain.privacy.repository.InterestRepository;
import com.api.ttoklip.domain.privacy.service.ProfileService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.security.auth.dto.request.AuthLoginRequest;
import com.api.ttoklip.global.security.auth.dto.request.AuthRequest;
import com.api.ttoklip.global.security.auth.dto.response.AuthLoginResponse;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final InterestRepository interestRepository;
    private static final String PROVIDER_LOCAL = "local";
    private final ProfileService profileService;
    private final S3FileUploader s3FileUploader;


    @Transactional
    public Message signup(final AuthRequest authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        String originName = authRequest.getOriginName();
        String nickname = authRequest.getNickname();
        int independentYear = authRequest.getIndependentYear();
        int independentMonth = authRequest.getIndependentMonth();
        String street = authRequest.getStreet();
        //List<Category> categories = authRequest.getCategories();
        MultipartFile profileImage = authRequest.getProfileImage();

        boolean isExist = memberRepository.existsByEmail(email);

        if (isExist) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_JOINID);
        }

        String registerPassword = bCryptPasswordEncoder.encode(password);
        log.info("---------------------" + registerPassword);

        Member newMember = Member.builder()
                .email(email)
                .password(registerPassword)
                .originName(originName)
                .nickname(nickname)
                .role(Role.CLIENT)
                .provider(PROVIDER_LOCAL)
                .independentYear(independentYear)
                .independentMonth(independentMonth)
                .street(street)
                .build();

        memberRepository.save(newMember);

        String profileImgUrl = s3FileUploader.uploadMultipartFile(profileImage);
        Profile profile = Profile.of(newMember, profileImgUrl);
        profileService.register(profile);
        registerInterest(authRequest, newMember);
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

    private void registerInterest(final AuthRequest authRequest, final Member currentMember) {
        List<Category> categories = authRequest.getCategories();
        List<Interest> interests = categories
                .stream()
                .map(category -> Interest.of(currentMember, category))
                .toList();

        interestRepository.saveAll(interests);

    }

}

