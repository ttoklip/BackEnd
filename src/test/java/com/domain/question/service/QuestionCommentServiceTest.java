package com.domain.question.service;

import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import com.api.ttoklip.domain.question.application.QuestionCommentService;
import com.domain.comment.repository.FakeCommentRepository;
import com.domain.question.repository.FakeQuestionPostRepository;
import org.junit.jupiter.api.BeforeEach;

public class QuestionCommentServiceTest {

    private QuestionCommentService questionCommentService;
    private CommentRepository commentRepository;
    private FakeQuestionPostRepository postRepository;

    @BeforeEach
    void setUp() {
        commentRepository = new FakeCommentRepository();
        questionCommentService = new QuestionCommentService(commentRepository);
        postRepository = new FakeQuestionPostRepository();
    }

}
