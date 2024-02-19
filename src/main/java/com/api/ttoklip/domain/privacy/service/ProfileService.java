package com.api.ttoklip.domain.privacy.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.editor.MemberEditor;
import com.api.ttoklip.domain.member.editor.MemberEditor.MemberEditorBuilder;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.privacy.domain.Interest;
import com.api.ttoklip.domain.privacy.domain.Profile;
import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.domain.privacy.repository.InterestRepository;
import com.api.ttoklip.domain.privacy.repository.ProfileRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final InterestRepository interestRepository;
    private final MemberService memberService;
    private final S3FileUploader s3FileUploader;

    // 회원가입시 자동으로 프로필 이미지 기입
    @Transactional
    public Long register(final Profile profile) {
        profileRepository.save(profile);
        return profile.getId();
    }

    // ------------- 회원 가입 후 입력 받을 닉네임, 우리동네 설정, 나의 동릭 경험, 관심 카테고리 선택 -------------
    @Transactional
    public Message insert(final PrivacyCreateRequest request) {

        validate(request);

        Member currentMember = insertPrivacy(request);

        registerInterest(request, currentMember);
        return Message.insertPrivacy();
    }

    private static void validate(final PrivacyCreateRequest request) {
        // 위 경도 중 하나라도 포함되어 있지 않은 경우
        if (request.getLocationX() == null || request.getLocationY() == null) {
            throw new ApiException(ErrorType.LOCATION_NOT_FOUND);
        }

        // 카테고리 없을 경우 에러 처리
        if (request.getCategories() == null && request.getCategories().isEmpty()) {
            throw new ApiException(ErrorType.CATEGORY_NOT_EXISTS);
        }
    }

    private Member insertPrivacy(final PrivacyCreateRequest request) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());

        updateProfileImage(request, currentMember);
        MemberEditor editor = getEditor(currentMember, request);
        currentMember.insertPrivacy(editor);
        return currentMember;
    }

    // ------------- 회원 가입 후 입력 받을 닉네임, 우리동네 설정, 나의 동릭 경험, 관심 카테고리 선택 -------------
    private void registerInterest(final PrivacyCreateRequest request, final Member currentMember) {
        interestRepository.deleteAllByMemberId(getCurrentMember().getId());

        List<Category> requestCategories = request.getCategories();
        List<Interest> interests = requestCategories
                .stream()
                .map(category -> Interest.of(currentMember, category))
                .toList();

        interestRepository.saveAll(interests);
    }

    @Transactional
    public Message edit(final PrivacyCreateRequest request) {
        Member currentMember = insertPrivacy(request);

        // 실제 존재할 때만 사용
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            registerInterest(request, currentMember);
        }

        return Message.insertPrivacy();
    }

    private void updateProfileImage(final PrivacyCreateRequest request, final Member currentMember) {
        // 프로필 이미지 URL 변경
        MultipartFile profileImage = request.getProfileImage();
        if (profileImage != null && !profileImage.isEmpty()) {
            String uploadUrl = s3FileUploader.uploadMultipartFile(profileImage);
            currentMember.getProfile().changeProfile(uploadUrl);
        }
    }

    private MemberEditor getEditor(final Member currentMember, final PrivacyCreateRequest request) {
        int independentYear = request.getIndependentYear();
        int independentMonth = request.getIndependentMonth();
        String nickname = request.getNickname();
        String street = request.getStreet();
        Integer locationX = request.getLocationX();
        Integer locationY = request.getLocationY();

        MemberEditorBuilder editorBuilder = currentMember.toEditor();
        return editorBuilder
                .independentYear(independentYear)
                .independentMonth(independentMonth)
                .street(street)
                .nickname(nickname)
                .locationX(locationX)
                .locationY(locationY)
                .build();
    }

    // ------------- 회원 가입 후 입력 받을 닉네임, 우리동네 설정, 나의 동릭 경험, 관심 카테고리 선택 끝 -------------


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
