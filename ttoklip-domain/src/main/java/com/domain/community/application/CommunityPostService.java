package com.domain.community.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.TownCriteria;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityEdit;
import com.domain.community.domain.CommunityEditor;
import com.domain.community.domain.CommunityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {

    private final CommunityRepository communityRepository;

    @Transactional
    public Community save(final Community community) {
        return communityRepository.save(community);
    }

    public Community findByIdFetchJoin(final Long postId) {
        return communityRepository.findByIdFetchJoin(postId);
    }

    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable, final String street) {
        return communityRepository.getPaging(townCriteria, pageable, street);
    }

    public Community getCommunity(final Long postId) {
        return communityRepository.findByIdActivated(postId);
    }

    public List<CommunityRecent3Response> getRecent3(final TownCriteria townCriteria, final String street) {
        List<Community> communities = communityRepository.getRecent3(townCriteria, street);
        return communities.stream()
                .map(CommunityRecent3Response::from)
                .toList();
    }

    @Transactional
    public void edit(final CommunityEdit request, final Community community, final Long memberId) {
        checkEditPermission(community, memberId);
        CommunityEditor postEditor = community.toEditor()
                .title(request.title())
                .content(request.content())
                .build();

        community.edit(postEditor);
    }

    private void checkEditPermission(final Community community, final Long memberId) {
        if (!community.getMember().getId().equals(memberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    @Transactional
    public void delete(final Community community, final Long memberId) {
        checkEditPermission(community, memberId);
        community.deactivate();
    }

    public Page<Community> getContain(final String keyword, final Pageable pageable, final String sort) {
        return communityRepository.getContain(keyword, pageable, sort);
    }

    public Page<Community> getMatchWriterPaging(final Long memberId, final Pageable pageable) {
        return communityRepository.getMatchWriterPaging(memberId, pageable);
    }
}
