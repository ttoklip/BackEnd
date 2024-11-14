package com.api.honeytip.facade;

import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipCommentService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipImageService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipLikeService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipScrapService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipUrlService;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.upload.infrastructure.S3FileUploader;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class HoneyTipFacadeTestHelper {

    @Mock
    protected MemberService memberService;

    @Mock
    protected HoneyTipPostService honeyTipPostService;

    @Mock
    protected HoneyTipUrlService honeyTipUrlService;

    @Mock
    protected HoneyTipImageService honeyTipImageService;

    @Mock
    protected ReportService reportService;

    @Mock
    protected S3FileUploader s3FileUploader;

    @Mock
    protected HoneyTipCommentService honeyTipCommentService;

    @Mock
    protected HoneyTipLikeService honeyTipLikeService;

    @Mock
    protected HoneyTipScrapService honeyTipScrapService;

    // 헬퍼 생성자에서 목 객체를 초기화
    public HoneyTipFacadeTestHelper() {
        MockitoAnnotations.openMocks(this);
    }
}