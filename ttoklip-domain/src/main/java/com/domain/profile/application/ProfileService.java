package com.domain.profile.application;

import com.domain.member.domain.Member;
import com.domain.member.domain.MemberEditor;
import com.domain.profile.application.response.PersonalInformation;
import com.domain.profile.domain.Profile;
import com.domain.profile.domain.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Long save(final Profile profile) {
        profileRepository.save(profile);
        return profile.getId();
    }

    public void update(final Member currentMember, final String uploadUrl) {
        currentMember.getProfile().changeProfile(uploadUrl);
    }

    public void registerPersonalInformation(final PersonalInformation information) {
        Member member = information.member();
        MemberEditor editor = member.toEditor()
                .independentYear(information.independentYear())
                .independentMonth(information.independentMonth())
                .street(information.street())
                .nickname(information.nickname())
                .build();
        member.insertPrivacy(editor);
    }
}
