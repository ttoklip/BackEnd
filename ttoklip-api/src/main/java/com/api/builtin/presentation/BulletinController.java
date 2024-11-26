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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class BulletinController implements BulletinControllerDocs {

    private final NoticeFacade noticeFacade;
    private final static int PAGE_SIZE = 10;

    @Override
    @GetMapping
    public TtoklipResponse<NoticeResponses> getNoticeList(
            @RequestParam(required = false, defaultValue = "0") int page) {
        NoticeResponses noticeResponses = noticeFacade.getNoticeList(page, PAGE_SIZE);
        return new TtoklipResponse<>(noticeResponses);
    }

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TtoklipResponse<Message> register(@Validated @RequestBody NoticeCreate request) {
        Message message = noticeFacade.register(request);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/{noticeId}")
    public TtoklipResponse<NoticeResponse> getSingleNotice(@PathVariable Long noticeId) {
        NoticeResponse singleNotice = noticeFacade.getSingleNotice(noticeId);
        return new TtoklipResponse<>(singleNotice);
    }

    @Override
    @DeleteMapping("/{noticeId}")
    public TtoklipResponse<Message> deleteNotice(@PathVariable Long noticeId) {
        Message message = noticeFacade.delete(noticeId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PatchMapping("/{noticeId}")
    public TtoklipResponse<Message> edit(@PathVariable Long noticeId, @RequestBody NoticeEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = noticeFacade.edit(noticeId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
