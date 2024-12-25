package com.api.builtin.presentation;

import com.api.builtin.application.NoticeFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.bulletin.domain.NoticeCreate;
import com.domain.bulletin.domain.NoticeEdit;
import com.domain.bulletin.domain.NoticeResponse;
import com.domain.bulletin.domain.NoticeResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class BulletinController implements BulletinControllerDocs {

    private final NoticeFacade noticeFacade;
    private final static int PAGE_SIZE = 10;

    @Override
    @GetMapping
    public TtoklipResponse<NoticeResponses> getNoticeList(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        return TtoklipResponse.ok(
                noticeFacade.getNoticeList(page, PAGE_SIZE)
        );
    }

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TtoklipResponse<Message> register(
            final @Validated @RequestBody NoticeCreate request
    ) {
        return TtoklipResponse.created(
                noticeFacade.register(request)
        );
    }

    @Override
    @GetMapping("/{noticeId}")
    public TtoklipResponse<NoticeResponse> getSingleNotice(
            final @PathVariable Long noticeId
    ) {
        return TtoklipResponse.ok(
                noticeFacade.getSingleNotice(noticeId)
        );
    }

    @Override
    @DeleteMapping("/{noticeId}")
    public TtoklipResponse<Message> deleteNotice(
            final @PathVariable Long noticeId
    ) {
        return TtoklipResponse.ok(
                noticeFacade.delete(noticeId)
        );
    }

    @Override
    @PatchMapping("/{noticeId}")
    public TtoklipResponse<Message> edit(
            final @PathVariable Long noticeId,
            final @RequestBody NoticeEdit request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                noticeFacade.edit(noticeId, request, currentMemberId)
        );
    }
}
