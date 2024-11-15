//package com.api.honeytip.facade;
//
//import com.domain.honeytip.application.HoneyTipCommentService;
//import com.domain.honeytip.application.HoneyTipImageService;
//import com.domain.honeytip.application.HoneyTipLikeService;
//import com.domain.honeytip.application.HoneyTipPostService;
//import com.domain.honeytip.application.HoneyTipScrapService;
//import com.domain.honeytip.application.HoneyTipUrlService;
//import com.domain.member.application.MemberService;
//import com.domain.report.application.ReportService;
//import com.infrastructure.aws.upload.infrastructure.S3FileUploader;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public abstract class HoneyTipFacadeTestHelper {
//
//    @Mock
//    protected MemberService memberService;
//
//    @Mock
//    protected HoneyTipPostService honeyTipPostService;
//
//    @Mock
//    protected HoneyTipUrlService honeyTipUrlService;
//
//    @Mock
//    protected HoneyTipImageService honeyTipImageService;
//
//    @Mock
//    protected ReportService reportService;
//
//    @Mock
//    protected S3FileUploader s3FileUploader;
//
//    @Mock
//    protected HoneyTipCommentService honeyTipCommentService;
//
//    @Mock
//    protected HoneyTipLikeService honeyTipLikeService;
//
//    @Mock
//    protected HoneyTipScrapService honeyTipScrapService;
//
//    // 헬퍼 생성자에서 목 객체를 초기화
//    public HoneyTipFacadeTestHelper() {
//        MockitoAnnotations.openMocks(this);
//    }
//}