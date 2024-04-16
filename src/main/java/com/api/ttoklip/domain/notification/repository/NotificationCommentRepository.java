package com.api.ttoklip.domain.notification.repository;

import static com.api.ttoklip.domain.common.comment.QComment.comment;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.honeyTip;
import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;
import static com.api.ttoklip.domain.question.post.domain.QQuestion.question;
import static com.api.ttoklip.domain.town.cart.comment.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.post.entity.QCart.cart;
import static com.api.ttoklip.domain.town.community.comment.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.post.entity.QCommunity.community;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationCommentRepository {

    private final JPAQueryFactory queryFactory;
    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;

    public Comment findParentCommentFetchJoin(final Long commentId) {
        Comment findComment = queryFactory
                .selectFrom(comment)
                .leftJoin(comment.parent, comment).fetchJoin()
                .leftJoin(comment.member, member).fetchJoin()
                .where(
                        comment.id.eq(commentId),
                        comment.deleted.isFalse()
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
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
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
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
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
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
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
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }
}
