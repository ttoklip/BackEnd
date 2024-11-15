package com.domain.cart.application;

import com.common.NotiCategory;
import com.common.annotation.SendCommentNotification;
import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartComment;
import com.domain.cart.domain.CartRepository;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentRepository;
import com.domain.member.domain.Member;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartCommentService {

    private final CartRepository cartRepository;
    private final CommentRepository commentRepository;

    public List<CartComment> findActiveCommentsByCartId(Long postId) {
        return cartRepository.findActiveCommentsByCartId(postId);
    }

    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.OUR_TOWN_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate request, final Cart findCart,
                                             final Comment parentComment, final Member member) {
        CartComment newCartComment = CartComment.withParentOf(request, parentComment, findCart, member);
        return commentRepository.save(newCartComment);
    }

    // 최상위 댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.OUR_TOWN_COMMENT)
    public Comment registerCommentOrphanage(final CommentCreate request, final Cart findCart, final Member member) {
        CartComment newCartComment = CartComment.orphanageOf(request, findCart, member);
        return commentRepository.save(newCartComment);
    }

}
