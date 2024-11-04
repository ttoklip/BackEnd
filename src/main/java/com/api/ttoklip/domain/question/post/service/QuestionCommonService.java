package com.api.ttoklip.domain.question.post.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.repository.QuestionRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.upload.Uploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommonService {

    private final Uploader uploader;
    private final QuestionRepository questionRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Question getQuestion(final Long postId) {
        return questionRepository.findByIdActivated(postId);
    }

    public QuestionComment getQuestionComment(final Long commentId) {
        return questionRepository.findByCommentIdActivated(commentId);
    }

    public List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return uploader.uploadMultipartFiles(uploadImages);
    }

    public void checkEditPermission(final Question question) {
        Long writerId = question.getMember().getId();
        Long currentMemberId = getCurrentMember().getId();

        if (!writerId.equals(currentMemberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */
}
