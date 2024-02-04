package com.api.ttoklip.domain.mypage.noti.post.service;

import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.mypage.noti.post.domain.NoticeRepository;
import com.api.ttoklip.domain.mypage.noti.post.domain.Notice;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeListResponse;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeResponse;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotiService {

    private final NoticeRepository noticeRepository;
    private final ReportService reportService;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Notice findNoticeById(final Long noticeId){
        return noticeRepository.findById(noticeId)
                .orElseThrow(()->new ApiException(ErrorType.NOTICE_NOT_FOUND));//notice 에러 추가 필요 SJ02.04
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final NoticeCreateRequest request){

        Notice notice=Notice.of(request);
        noticeRepository.save(notice);
        return notice.getId();
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public NoticeResponse getSingleNotice(final Long noticeId) {
        Notice notice=findNoticeById(noticeId);
        NoticeResponse noticeResponse = NoticeResponse.of(notice);//추후 구현
        return noticeResponse;
    }

    public NoticeListResponse noticeList(){
        return null;
    }
}
