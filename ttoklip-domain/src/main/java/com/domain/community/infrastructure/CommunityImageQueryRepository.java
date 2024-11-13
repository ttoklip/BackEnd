package com.domain.community.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.community.domain.CommunityImage;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityImageQueryRepository {

    private final JPAQueryFactory queryFactory;

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

    public boolean doAllImageIdsExist(List<Long> imageIds) {
        Long count = queryFactory
                .select(Wildcard.count)
                .from(communityImage)
                .where(communityImage.id.in(imageIds))
                .fetchOne();

        return count != null && count == imageIds.size();
    }

    public void deleteByImageIds(List<Long> imageIds) {
        queryFactory
                .delete(communityImage)
                .where(communityImage.id.in(imageIds))
                .execute();
    }
}
