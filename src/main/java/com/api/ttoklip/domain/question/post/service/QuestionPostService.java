package com.api.ttoklip.domain.question.post.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.repository.QuestionRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostService {

    private final QuestionRepository questionRepository;

    //    /* -------------------------------------------- COMMON -------------------------------------------- */
    //    public Question findQuestionById(final Long postId) {
    //        return questionRepository.findById(postId)
    //                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
    //    }
    //
    //    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

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
