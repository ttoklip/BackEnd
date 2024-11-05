package com.domain.question.facade;

import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.question.service.QuestionCommentLikeService;
import com.api.ttoklip.domain.question.service.QuestionCommentService;
import com.api.ttoklip.domain.question.service.QuestionImageService;
import com.api.ttoklip.domain.question.service.QuestionPostService;
import com.api.ttoklip.global.upload.Uploader;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class QuestionFacadeTestHelper {

    @Mock
    protected MemberService memberService;

    @Mock
    protected QuestionPostService questionPostService;

    @Mock
    protected QuestionImageService questionImageService;

    @Mock
    protected ReportService reportService;

    @Mock
    protected Uploader uploader;

    @Mock
    protected QuestionCommentService questionCommentService;

    @Mock
    protected QuestionCommentLikeService questionCommentLikeService;

    // 헬퍼 생성자에서 목 객체를 초기화
    public QuestionFacadeTestHelper() {
        MockitoAnnotations.openMocks(this);
    }
}
