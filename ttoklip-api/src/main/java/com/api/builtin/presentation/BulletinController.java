package com.api.builtin.presentation;

import com.api.builtin.application.NoticeFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.bulletin.domain.NoticeCreate;
import com.domain.bulletin.domain.NoticeEdit;
import com.domain.bulletin.domain.NoticeResponse;
import com.domain.bulletin.domain.NoticeResponses;
import com.api.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BulletinController implements BulletinControllerDocs {

    private final NoticeFacade noticeFacade;
    private final static int PAGE_SIZE = 10;

    @Override
    public TtoklipResponse<NoticeResponses> getNoticeList(int page) {
        NoticeResponses noticeResponses = noticeFacade.getNoticeList(page, PAGE_SIZE);
        return new TtoklipResponse<>(noticeResponses);
    }

    @Override
    public TtoklipResponse<Message> register(NoticeCreate request) {
        Message message = noticeFacade.register(request);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<NoticeResponse> getSingleNotice(Long noticeId) {
        NoticeResponse singleNotice = noticeFacade.getSingleNotice(noticeId);
        return new TtoklipResponse<>(singleNotice);
    }

    @Override
    public TtoklipResponse<Message> deleteNotice(Long noticeId) {
        Message message = noticeFacade.delete(noticeId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> edit(Long noticeId, NoticeEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = noticeFacade.edit(noticeId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
