package com.api.auth.local.application;

import com.api.auth.local.presentation.AuthLogin;
import com.common.annotation.DistributedLock;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.interest.application.InterestService;
import com.domain.member.domain.LocalMemberCreate;
import com.api.auth.local.presentation.LocalMemberWebCreate;
import com.api.auth.local.presentation.AuthLoginResponse;
import com.api.common.upload.Uploader;
import com.api.global.success.Message;
import com.common.jwt.TokenProvider;
import com.domain.common.vo.Category;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.profile.application.ProfileService;
import com.domain.profile.domain.Profile;
import com.domain.term.application.TermAgreementService;
import com.domain.term.application.TermService;
import com.domain.term.domain.Term;
import com.domain.term.domain.TermAgreement;
import com.domain.term.response.TermSignUpResponse;
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

@Slf4j
@Service
@AllArgsConstructor
public class AuthFacade {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final TokenProvider provider;
    private final InterestService interestService;
    private final ProfileService profileService;
    private final Uploader uploader;
    private final TermService termService;
    private final TermAgreementService termAgreementService;

    @Transactional
    @DistributedLock(keyPrefix = "local-signup")
    public Message signup(final LocalMemberWebCreate request) {

        Member newMember = registerMember(request);
        registerProfile(request.profileImage(), newMember);
        registerInterest(newMember, request.getCategories());
        registerTermAgreements(request, newMember);
        return Message.registerUser();
    }

    private Member registerMember(final LocalMemberWebCreate request) {
        String encodedPassword = bCryptPasswordEncoder.encode(request.password());
        LocalMemberCreate create = LocalMemberCreate.of(request.email(), encodedPassword, request.originName(),
                request.nickname(),
                request.independentYear(), request.independentMonth(), request.street(), request.agreeTermsOfService(),
                request.agreePrivacyPolicy(), request.agreeLocationService(), request.getCategories());
        return memberService.registerLocalMember(create);
    }

    private void registerProfile(final MultipartFile profileImage, final Member newMember) {
        String profileImgUrl = uploader.uploadMultipartFile(profileImage);
        Profile profile = Profile.of(newMember, profileImgUrl);
        profileService.save(profile);
    }

    @Transactional
    public Message duplicate(final String email) {
        memberService.validateEmail(email);
        return Message.validId();
    }

    public AuthLoginResponse login(final AuthLogin authLogin) {
        Member loginMember = authenticate(authLogin);
        String jwtToken = provider.create(loginMember.getEmail());
        boolean existsNickname = memberService.isExistsNickname(loginMember.getNickname());
        if (existsNickname) {
            return getLoginResponse(jwtToken, false);
        }

        return getLoginResponse(jwtToken, true);
    }

    private Member authenticate(final AuthLogin request) {
        String email = request.email();
        String password = request.password();
        Member findMember = memberService.findByEmail(email);
        if (!bCryptPasswordEncoder.matches(password, findMember.getPassword())) {
            throw new ApiException(ErrorType.AUTH_INVALID_PASSWORD);
        }

        return findMember;
    }

    private AuthLoginResponse getLoginResponse(final String jwtToken, final boolean ifFirstLogin) {
        return AuthLoginResponse.builder()
                .jwtToken(jwtToken)
                .ifFirstLogin(ifFirstLogin)
                .build();
    }

    private void registerInterest(final Member member, final List<Category> categories) {
        interestService.registerInterest(member, categories);
    }

    private void registerTermAgreements(final LocalMemberWebCreate request, final Member newMember) {
        List<TermAgreement> termAgreements = Stream.of(
                createTermAgreement(request.agreeTermsOfService(), newMember, termService::getAgreeTermsOfService),
                createTermAgreement(request.agreePrivacyPolicy(), newMember, termService::getAgreePrivacyPolicy),
                createTermAgreement(request.agreeLocationService(), newMember, termService::getAgreeLocationService)
        ).flatMap(Optional::stream).toList();

        termAgreementService.regsiterAgreements(termAgreements);
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

