package com.domain.question.service;

import com.api.ttoklip.domain.question.post.repository.QuestionRepository;
import com.api.ttoklip.domain.question.post.service.QuestionPostService;
import com.domain.question.repository.FakeQuestionPostRepository;
import org.junit.jupiter.api.BeforeEach;

public class QuestionPostServiceTest {

    private QuestionPostService questionPostService;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        questionRepository = new FakeQuestionPostRepository();
        questionPostService = new QuestionPostService(null, questionRepository);
    }

}
