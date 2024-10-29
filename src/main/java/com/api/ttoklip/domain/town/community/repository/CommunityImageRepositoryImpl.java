package com.api.ttoklip.domain.town.community.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.town.community.domain.QCommunity.community;
import static com.api.ttoklip.domain.town.community.domain.QCommunityImage.communityImage;

import com.api.ttoklip.domain.town.community.domain.CommunityImage;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommunityImageRepositoryImpl implements CommunityImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void allImageOwner(List<Long> imageIds, Long memberId) {
        List<CommunityImage> communityImages = queryFactory
                .select(communityImage)
                .from(communityImage)
                .leftJoin(communityImage.community, community).fetchJoin()
                .leftJoin(communityImage.community.member, member).fetchJoin()
                .where(communityImage.id.in(imageIds))
                .fetch();

        boolean isOwner = communityImages.stream()
                .allMatch(
                        singleCommunityImage -> singleCommunityImage.getCommunity().getMember().getId().equals(memberId)
                );
        if (!isOwner) {
            throw new ApiException(ErrorType.INVALID_DELETE_IMAGE_OWNER);
        }
    }

    @Override
    public boolean doAllImageIdsExist(List<Long> imageIds) {
        Long count = queryFactory
                .select(Wildcard.count)
                .from(communityImage)
                .where(communityImage.id.in(imageIds))
                .fetchOne();

        return count != null && count == imageIds.size();
    }

    @Override
    public void deleteByImageIds(List<Long> imageIds) {
        queryFactory
                .delete(communityImage)
                .where(communityImage.id.in(imageIds))
                .execute();
    }
}
