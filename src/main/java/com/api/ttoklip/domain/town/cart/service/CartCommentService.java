package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.town.cart.domain.CartComment;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.cart.repository.CartRepository;
import com.api.ttoklip.global.success.Message;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartCommentService {

    private final CartRepository cartRepository;

    public List<CartComment> findActiveCommentsByCartId(Long postId) {
        return cartRepository.findActiveCommentsByCartId(postId);
    }

}
