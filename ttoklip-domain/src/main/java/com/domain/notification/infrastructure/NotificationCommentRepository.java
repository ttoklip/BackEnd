package com.domain.notification.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.cart.domain.CartComment;
import com.domain.comment.domain.Comment;
import com.domain.community.domain.CommunityComment;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.question.domain.QuestionComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationCommentRepository {

    private final JPAQueryFactory queryFactory;

    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;
    private final QQuestionComment questionComment = QQuestionComment.questionComment;
    private final QCommunityComment communityComment = QCommunityComment.communityComment;
    private final QCartComment cartComment = QCartComment.cartComment;
    private final QComment comment = QComment.comment;

    private final QCommunity community = QCommunity.community;
    private final QQuestion question = QQuestion.question;
    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QCart cart = QCart.cart;
    private final QMember member = QMember.member;

    public Comment findParentCommentFetchJoin(final Long commentId) {
        QComment childComment = comment;
        QComment parentComment = comment.parent;

        Comment findComment = queryFactory
                .selectFrom(childComment)
                .leftJoin(parentComment, childComment).fetchJoin()
                .leftJoin(childComment.member, member).fetchJoin()
                .where(
                        childComment.id.eq(commentId),
                        childComment.deleted.isFalse()
                )
                .distinct()
                .fetchOne();
        return Optional.ofNullable(findComment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    public HoneyTipComment findHoneyTipCommentFetchJoin(final Long honeyTipCommentId) {
        HoneyTipComment findHoneyTipComment = queryFactory
                .selectFrom(honeyTipComment)
                .distinct()
                .leftJoin(honeyTipComment.honeyTip, honeyTip).fetchJoin()
                .leftJoin(honeyTipComment.honeyTip.member, member).fetchJoin()
                .where(honeyTipComment.id.eq(honeyTipCommentId))
                .fetchOne();
        return Optional.ofNullable(findHoneyTipComment)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_COMMENT_NOT_FOUND));
    }

    public QuestionComment findQuestionCommentFetchJoin(final Long questionCommentId) {
        QuestionComment findHoneyTipComment = queryFactory
                .selectFrom(questionComment)
                .distinct()
                .leftJoin(questionComment.question, question).fetchJoin()
                .leftJoin(questionComment.question.member, member).fetchJoin()
                .where(questionComment.id.eq(questionCommentId))
                .fetchOne();
        return Optional.ofNullable(findHoneyTipComment)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_COMMENT_NOT_FOUND));
    }

    public CartComment findCartCommentFetchJoin(final Long cartCommentId) {
        CartComment findCartComment = queryFactory
                .selectFrom(cartComment)
                .distinct()
                .leftJoin(cartComment.cart, cart).fetchJoin()
                .leftJoin(cartComment.cart.member, member).fetchJoin()
                .where(cartComment.id.eq(cartCommentId))
                .fetchOne();
        return Optional.ofNullable(findCartComment)
                .orElseThrow(() -> new ApiException(ErrorType.CART_COMMENT_NOT_FOUND));
    }

    public CommunityComment findCommunityCommentFetchJoin(final Long communityCommentId) {
        CommunityComment findCommunityComment = queryFactory
                .selectFrom(communityComment)
                .distinct()
                .leftJoin(communityComment.community, community).fetchJoin()
                .leftJoin(communityComment.community.member, member).fetchJoin()
                .where(communityComment.id.eq(communityCommentId))
                .fetchOne();
        return Optional.ofNullable(findCommunityComment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_COMMENT_NOT_FOUND));
    }
}
