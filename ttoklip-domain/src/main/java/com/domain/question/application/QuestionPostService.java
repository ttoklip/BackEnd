package com.domain.question.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionComment;
import com.domain.question.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostService {

    private final QuestionRepository questionRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Question getQuestion(final Long postId) {
        return questionRepository.findByIdActivated(postId);
    }

    public QuestionComment getQuestionComment(final Long commentId) {
        return questionRepository.findByCommentIdActivated(commentId);
    }

    public void checkEditPermission(final Question question, final Long memberId) {
        Long writerId = question.getMember().getId();

        if (!writerId.equals(memberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

    @Transactional
    public Question saveQuestionPost(final Question question) {
        return questionRepository.save(question);
    }


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public void saveQuestion(final Question question) {
        questionRepository.save(question);
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public Question findByIdFetchJoin(final Long postId) {
        return questionRepository.findByIdFetchJoin(postId);
    }

    /* -------------------------------------------- 단건 READ 끝-------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */
    public List<Question> getHouseWork() {
        return questionRepository.getHouseWork();
    }

    public List<Question> getRecipe() {
        return questionRepository.getRecipe();
    }

    public List<Question> getSafeLiving() {
        return questionRepository.getSafeLiving();
    }

    public List<Question> getWelfarePolicy() {
        return questionRepository.getWelfarePolicy();
    }
    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */


    // ------------------------------------ 메인 페이지 꿀팁공유해요 카테고리별 페이징 조회 ------------------------------------

    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
        return questionRepository.matchCategoryPaging(category, pageable);
    }
    // ------------------------------------ 메인 페이지 꿀팁공유해요 카테고리별 페이징 조회 끝 ------------------------------------
}
