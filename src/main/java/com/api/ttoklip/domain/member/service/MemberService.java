package com.api.ttoklip.domain.member.service;

import static com.api.ttoklip.global.exception.ErrorType._USER_NOT_FOUND_BY_TOKEN;
import static com.api.ttoklip.global.exception.ErrorType._USER_NOT_FOUND_DB;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.repository.MemberOAuthRepository;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.success.Message;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberOAuthRepository memberOAuthRepository;

    public Member findById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(_USER_NOT_FOUND_DB));
    }

    public Member findByIdWithProfile(final Long memberId) {
        return memberOAuthRepository.findByIdWithProfile(memberId);
    }

    public Member findByNickNameWithProfile(final String nickName){
        return memberOAuthRepository.findByNickNameWithProfile(nickName);//02.17
    }

    public boolean isExistsNickname(final String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(_USER_NOT_FOUND_BY_TOKEN));
    }

    public Optional<Member> findByEmailOptional(final String email) {
        return memberRepository.findByEmail(email);

    }

    @Transactional
    public void register(final Member member) {
        memberRepository.save(member);
    }

    public Message updateMemberFCMToken(final Member member, final String fcmToken) {
        member.updateFcmToken(fcmToken);
        return Message.updateFCM();
    }
}
