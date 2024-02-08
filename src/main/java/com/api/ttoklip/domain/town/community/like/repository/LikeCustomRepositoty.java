package com.api.ttoklip.domain.town.community.like.repository;

import com.api.ttoklip.domain.Member;
import com.api.ttoklip.domain.town.community.like.entity.Like;
import com.api.ttoklip.domain.town.community.post.entity.Community;

public interface LikeCustomRepositoty {

    Like findByMemberAndCommunity(Member member, Community community);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

}
