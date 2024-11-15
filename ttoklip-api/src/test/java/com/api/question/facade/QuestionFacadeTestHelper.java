//package com.api.question.facade;
//
//import com.domain.member.application.MemberService;
//import com.domain.question.application.QuestionCommentLikeService;
//import com.domain.question.application.QuestionCommentService;
//import com.domain.question.application.QuestionImageService;
//import com.domain.question.application.QuestionPostService;
//import com.domain.report.application.ReportService;
//import com.infrastructure.aws.upload.infrastructure.S3FileUploader;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public abstract class QuestionFacadeTestHelper {
//
//    @Mock
//    protected MemberService memberService;
//
//    @Mock
//    protected QuestionPostService questionPostService;
//
//    @Mock
//    protected QuestionImageService questionImageService;
//
//    @Mock
//    protected ReportService reportService;
//
//    @Mock
//    protected S3FileUploader s3FileUploader;
//
//    @Mock
//    protected QuestionCommentService questionCommentService;
//
//    @Mock
//    protected QuestionCommentLikeService questionCommentLikeService;
//
//    // 헬퍼 생성자에서 목 객체를 초기화
//    public QuestionFacadeTestHelper() {
//        MockitoAnnotations.openMocks(this);
//    }
//}
