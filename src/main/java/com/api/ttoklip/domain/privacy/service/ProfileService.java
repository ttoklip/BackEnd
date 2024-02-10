package com.api.ttoklip.domain.privacy.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.editor.MemberEditor;
import com.api.ttoklip.domain.member.editor.MemberEditor.MemberEditorBuilder;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.privacy.domain.Interest;
import com.api.ttoklip.domain.privacy.domain.Profile;
import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.domain.privacy.repository.InterestRepository;
import com.api.ttoklip.domain.privacy.repository.ProfileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final InterestRepository interestRepository;
    private final MemberService memberService;

    // 회원가입시 자동으로 프로필 이미지 기입
    @Transactional
    public Long register(final Profile profile) {
        profileRepository.save(profile);
        return profile.getId();
    }

    // 회원 가입 후 입력 받을 닉네임, 우리동네 설정, 나의 동릭 경험, 관심 카테고리 선택
    @Transactional
    public void insert(final PrivacyCreateRequest request) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        MemberEditor editor = getEditor(currentMember, request);
        currentMember.insertPrivacy(editor);
        registerInterest(request, currentMember);
    }

    private MemberEditor getEditor(final Member currentMember, final PrivacyCreateRequest request) {
        int independentYear = request.getIndependentYear();
        int independentMonth = request.getIndependentMonth();
        String nickname = request.getNickname();    // ToDo 추후에 닉네임 중복되는지 검사하는 로직 처리
        //        String street = request.getStreet(); // ToDo 추후에 Embeded로 주소 처리
        MemberEditorBuilder editorBuilder = currentMember.toEditor();
        return editorBuilder
                .independentYear(independentYear)
                .independentMonth(independentMonth)
                .nickname(nickname)
                .build();
    }

    private void registerInterest(final PrivacyCreateRequest request, final Member currentMember) {
        List<Category> requestCategories = request.getCategories();
        List<Interest> interests = requestCategories
                .stream()
                .map(category -> Interest.of(currentMember, category))
                .toList();
        interestRepository.saveAll(interests);
    }
}
