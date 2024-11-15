package com.domain.comment.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentEdit;
import com.domain.comment.domain.CommentEditor;
import com.domain.comment.domain.CommentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Comment register(final Comment comment) {
        boolean isChildComment = comment.getParent() != null;
        if (isChildComment && comment.getParent().isDeleted()) {
            throw new ApiException(ErrorType.COMMENT_IS_DISABLE);
        }
        return commentRepository.save(comment);
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- READ 부모 댓글 -------------------------------------------- */
    public Optional<Comment> findParentComment(final Long parentCommentId) {
        if (parentCommentId != null) {
            return commentRepository.findByIdActivatedOptional(parentCommentId);
        }
        return Optional.empty();
    }

    /* -------------------------------------------- READ 부모 댓글 끝 -------------------------------------------- */

    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    public void edit(final Long commentId, final CommentEdit request) {

        Comment comment = findComment(commentId);
        // ToDo 작성자가 맞는지 검증 필요
        CommentEditor commentEditor = getCommentEditor(request, comment);
        comment.edit(commentEditor);
    }

    public Comment findComment(final Long commentId) {
        return commentRepository.findByIdActivated(commentId);
    }

    private CommentEditor getCommentEditor(final CommentEdit request, final Comment comment) {
        return comment.toEditor()
                .comment(request.getComment())
                .build();
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public void deleteById(final Long commentId, final Long memberId) {
        Comment comment = findComment(commentId);
        checkDeletePermission(comment, memberId);
        comment.deactivate();
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

    public void checkDeletePermission(final Comment comment, final Long memberId) {
        Long writerId = comment.getMember().getId();
        if (!writerId.equals(memberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_DELETE_COMMENT);
        }
    }
}
