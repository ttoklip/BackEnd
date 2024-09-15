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
import com.api.ttoklip.domain.term.domain.Term;
import com.api.ttoklip.domain.term.domain.TermAgreement;
import com.api.ttoklip.domain.term.service.TermService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.security.auth.dto.request.AuthLoginRequest;
import com.api.ttoklip.global.security.auth.dto.request.AuthRequest;
import com.api.ttoklip.global.security.auth.dto.response.AuthLoginResponse;
import com.api.ttoklip.global.security.auth.dto.response.TermSignUpResponse;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
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

    private static final String PROVIDER_LOCAL = "local";
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final InterestRepository interestRepository;
    private final ProfileService profileService;
    private final S3FileUploader s3FileUploader;
    private final TermService termService;

    @Transactional
    public Message signup(final AuthRequest authRequest) {
        Member newMember = registerMember(authRequest);

        MultipartFile profileImage = authRequest.getProfileImage();
        String profileImgUrl = s3FileUploader.uploadMultipartFile(profileImage);
        registerProfile(newMember, profileImgUrl);

        registerInterest(authRequest.getCategories(), newMember);
        registerTermAgreements(authRequest, newMember);

        return Message.registerUser();
    }

    private Member registerMember(final AuthRequest authRequest) {
        String email = authRequest.getEmail();
        validateEmail(email);

        String password = authRequest.getPassword();
        String registerPassword = bCryptPasswordEncoder.encode(password);
        String originName = authRequest.getOriginName();
        String nickname = authRequest.getNickname();
        int independentYear = authRequest.getIndependentYear();
        int independentMonth = authRequest.getIndependentMonth();
        String street = authRequest.getStreet();

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
        return newMember;
    }

    private void validateEmail(final String email) {
        boolean isExist = memberRepository.existsByEmail(email);

        if (isExist) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_JOIN_ID);
        }
    }

    private void registerProfile(final Member newMember, final String profileImgUrl) {
        Profile profile = Profile.of(newMember, profileImgUrl);
        profileService.register(profile);
    }

    @Transactional
    public Message duplicate(final String email) {
        validateEmail(email);
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

    private void registerInterest(final List<Category> categories, final Member currentMember) {
        List<Interest> interests = categories
                .stream()
                .map(category -> Interest.of(currentMember, category))
                .toList();

        interestRepository.saveAll(interests);
    }

    private void registerTermAgreements(final AuthRequest request, final Member newMember) {
        List<TermAgreement> termAgreements = Stream.of(
                createTermAgreement(request.isAgreeTermsOfService(), newMember, termService::getAgreeTermsOfService),
                createTermAgreement(request.isAgreePrivacyPolicy(), newMember, termService::getAgreePrivacyPolicy),
                createTermAgreement(request.isAgreeLocationService(), newMember, termService::getAgreeLocationService)
        ).flatMap(Optional::stream).toList();

        termService.saveTermAgreementRepository(termAgreements);
//        termService.saveTermAgreementRepository(termAgreements, newMember);
    }

    private Optional<TermAgreement> createTermAgreement(boolean isAgreed, Member member, Supplier<Term> termSupplier) {
        if (isAgreed) {
            Term term = termSupplier.get();
            return Optional.of(
                    TermAgreement.of(member, term)
            );
        }
        return Optional.empty();
    }

    public TermSignUpResponse getTermWhenSignUp() {
        return termService.getTermWhenSignUp();
    }

}

