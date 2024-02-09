package com.api.ttoklip.domain.member.service;

import static com.api.ttoklip.global.exception.ErrorType._USER_NOT_FOUND_BY_TOKEN;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.repository.MemberOAuthRepository;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberOAuthRepository memberOAuthRepository;

    public Member findByIdOfToken(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(_USER_NOT_FOUND_BY_TOKEN));
    }

    public Member findByIdWithProfile(final Long memberId) {
        return memberOAuthRepository.findByIdWithProfile(memberId);
    }
}
