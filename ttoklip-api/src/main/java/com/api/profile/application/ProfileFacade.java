package com.api.profile.application;

import com.api.common.upload.Uploader;
import com.api.global.success.Message;
import com.api.profile.presentation.ProfileWebCreate;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.interest.application.InterestService;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.profile.application.response.PersonalInformation;
import com.domain.profile.application.ProfileService;
import com.domain.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileFacade {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final InterestService interestService;
    private final Uploader uploader;

    @Transactional
    public Long register(final Profile profile) {
        profileService.save(profile);
        return profile.getId();
    }

    // ------------- 회원 가입 후 입력 받을 닉네임, 우리동네 설정, 나의 동릭 경험, 관심 카테고리 선택 -------------

    @Transactional
    public Message insert(final ProfileWebCreate create, final Long memberId) {
        validate(create);
        updateProfile(create, memberId);
        return Message.insertPrivacy();
    }

    private void validate(final ProfileWebCreate create) {
        if (create.getCategories() == null && create.getCategories().isEmpty()) {
            throw new ApiException(ErrorType.CATEGORY_NOT_EXISTS);
        }
    }

    private void updateProfile(final ProfileWebCreate create, final Long memberId) {
        Member member = memberService.findByIdWithProfile(memberId);
        updateProfileImage(member, create);
        registerPersonalInfoAndInterests(create, member);
    }

    private void updateProfileImage(final Member member, final ProfileWebCreate request) {
        MultipartFile profileImage = request.profileImage();
        if (profileImage != null && !profileImage.isEmpty()) {
            String uploadUrl = uploader.uploadMultipartFile(profileImage);
            profileService.update(member, uploadUrl);
        }
    }

    private void registerPersonalInfoAndInterests(final ProfileWebCreate create, final Member member) {
        PersonalInformation information = PersonalInformation.from(
                member,
                create.independentYear(),
                create.independentMonth(),
                create.nickname(),
                create.street());
        profileService.registerPersonalInformation(information);
        interestService.registerInterest(member, create.getCategories());
    }

    // ------------- 회원 가입 후 입력 받을 닉네임, 우리동네 설정, 나의 동릭 경험, 관심 카테고리 선택 끝 -------------

    @Transactional
    public Message edit(final ProfileWebCreate create, final Long memberId) {
        validate(create);
        Member member = memberService.getById(memberId);
        registerPersonalInfoAndInterests(create, member);
        return Message.insertPrivacy();
    }

    // -------------------------- 회원 가입 전 닉네임 중복 확인 --------------------------
    public Message validNickname(final String nickname) {
        boolean existsNickname = memberService.isExistsNickname(nickname);
        if (existsNickname) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_NICKNAME);
        }
        return Message.validNickname();
    }
    // -------------------------- 회원 가입 전 닉네임 중복 확인 끝 --------------------------
}
