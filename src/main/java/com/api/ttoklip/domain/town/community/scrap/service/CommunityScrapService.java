package com.api.ttoklip.domain.town.community.scrap.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.service.CommunityCommonService;
import com.api.ttoklip.domain.town.community.scrap.entity.CommunityScrap;
import com.api.ttoklip.domain.town.community.scrap.repository.CommunityScrapRepository;
import com.api.ttoklip.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityScrapService {

    private final CommunityScrapRepository communityScrapRepository;
    private final CommunityCommonService communityCommonService;

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    public void register(final Long communityId) {
        // 좋아요 생성
        Community findCommunity = communityCommonService.getCommunity(communityId);
        Member currentMember = getCurrentMember();

        CommunityScrap communityScrap = CommunityScrap.of(currentMember, findCommunity);
        communityScrapRepository.save(communityScrap);
    }

}
