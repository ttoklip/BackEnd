package com.api.ttoklip.domain.common.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.editor.CommentEditor;
import com.api.ttoklip.domain.common.comment.editor.CommentEditor.CommentEditorBuilder;
import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
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
    public void register(final Comment comment) {
        commentRepository.save(comment);
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- READ 부모 댓글 -------------------------------------------- */
    public Optional<Comment> findParentComment(final Long parentCommentId) {
        if (parentCommentId != null) {
            return commentRepository.findById(parentCommentId);
        }
        return Optional.empty();
    }

    /* -------------------------------------------- READ 부모 댓글 끝 -------------------------------------------- */

    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    public void edit(final Long commentId, final CommentEditRequest request) {

        Comment comment = findComment(commentId);
        // ToDo 작성자가 맞는지 검증 필요
        CommentEditor commentEditor = getCommentEditor(request, comment);
        comment.edit(commentEditor);
    }

    public Comment findComment(final Long commentId) {
        return commentRepository.findByIdActivated(commentId);
    }

    private CommentEditor getCommentEditor(final CommentEditRequest request, final Comment comment) {
        CommentEditorBuilder editorBuilder = comment.toEditor();
        CommentEditor commentEditor = editorBuilder
                .comment(request.getComment())
                .build();
        return commentEditor;
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public void deleteById(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        Comment comment = findComment(commentId);
        comment.deactivate();
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
