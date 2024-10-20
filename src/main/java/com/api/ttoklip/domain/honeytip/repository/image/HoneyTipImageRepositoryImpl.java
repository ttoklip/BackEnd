package com.api.ttoklip.domain.honeytip.repository.image;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTip;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTipImage;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipImageRepositoryImpl implements HoneyTipImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QHoneyTipImage honeyTipImage = QHoneyTipImage.honeyTipImage;
    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QMember member = QMember.member;

    @Override
    public void allImageOwner(List<Long> imageIds, Long memberId) {
        List<HoneyTipImage> honeyTipImages = queryFactory
                .select(honeyTipImage)
                .from(honeyTipImage)
                .leftJoin(honeyTipImage.honeyTip, honeyTip).fetchJoin()
                .leftJoin(honeyTipImage.honeyTip.member, member).fetchJoin()
                .where(honeyTipImage.id.in(imageIds))
                .fetch();

        boolean isOwner = honeyTipImages.stream()
                .allMatch(
                        singleHoneyTipImage -> singleHoneyTipImage.getHoneyTip().getMember().getId().equals(memberId)
                );
        if (!isOwner) {
            throw new ApiException(ErrorType.INVALID_DELETE_IMAGE_OWNER);
        }
    }

    @Override
    public boolean doAllImageIdsExist(List<Long> imageIds) {
        Long count = queryFactory
                .select(Wildcard.count)
                .from(honeyTipImage)
                .where(honeyTipImage.id.in(imageIds))
                .fetchOne();

        return count != null && count == imageIds.size();
    }

    @Override
    public void deleteByImageIds(List<Long> imageIds) {
        queryFactory
                .delete(honeyTipImage)
                .where(honeyTipImage.id.in(imageIds))
                .execute();
    }
}
