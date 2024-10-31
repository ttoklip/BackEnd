package com.api.ttoklip.domain.town.community.service;

import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.community.controller.dto.response.CommunityRecent3Response;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {

    private final CommunityRepository communityRepository;

    @Transactional
    public Community saveCommunityPost(final Community community) {
        return communityRepository.save(community);
    }

    public Community findByIdFetchJoin(final Long postId) {
        return communityRepository.findByIdFetchJoin(postId);
    }

    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable) {
        return communityRepository.getPaging(townCriteria, pageable);
    }

    public Community getCommunity(final Long postId) {
        return communityRepository.findByIdActivated(postId);
    }

//    public List<Community> getRecent3(final TownCriteria townCriteria) {
//        return communityRepository.getRecent3(townCriteria);
//    }

    //Todo Service가 분리되어있는게 맞는지
    public List<CommunityRecent3Response> getRecent3(final TownCriteria townCriteria) {
        List<Community> communities = communityRepository.getRecent3(townCriteria);
        return communities.stream()
                .map(CommunityRecent3Response::from)
                .toList();
    }

}
