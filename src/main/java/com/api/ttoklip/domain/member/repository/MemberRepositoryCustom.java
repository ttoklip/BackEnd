package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;

public interface MemberRepositoryCustom {

    Member getTargetMemberProfile(Long targetMemberId);
}
