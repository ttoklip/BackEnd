package com.api.ttoklip.domain.question.repository.image;

import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.question.domain.QQuestion;
import com.api.ttoklip.domain.question.domain.QQuestionImage;
import com.api.ttoklip.domain.question.domain.QuestionImage;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionImageQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QQuestionImage questionImage = QQuestionImage.questionImage;
    private final QQuestion question = QQuestion.question;
    private final QMember member = QMember.member;

    public void verifyMemberIsImageOwner(List<Long> imageIds, Long memberId) {
        List<QuestionImage> questionImages = queryFactory
                .select(questionImage)
                .from(questionImage)
                .leftJoin(questionImage.question, question).fetchJoin()
                .leftJoin(questionImage.question.member, member).fetchJoin()
                .where(questionImage.id.in(imageIds))
                .fetch();

        boolean isOwner = questionImages.stream()
                .allMatch(
                        singleQuestionImage -> singleQuestionImage.getQuestion().getMember().getId().equals(memberId)
                );
        if (!isOwner) {
            throw new ApiException(ErrorType.INVALID_DELETE_IMAGE_OWNER);
        }
    }

    public boolean doAllImageIdsExist(List<Long> imageIds) {
        Long count = queryFactory
                .select(Wildcard.count)
                .from(questionImage)
                .where(questionImage.id.in(imageIds))
                .fetchOne();

        return count != null && count == imageIds.size();
    }

    public void deleteByImageIds(List<Long> imageIds) {
        queryFactory
                .delete(questionImage)
                .where(questionImage.id.in(imageIds))
                .execute();
    }
}
