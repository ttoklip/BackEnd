package com.api.builtin.application;

import com.api.global.success.Message;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.bulletin.application.NoticeService;
import com.domain.bulletin.domain.Notice;
import com.domain.bulletin.domain.NoticeCreate;
import com.domain.bulletin.domain.NoticeEdit;
import com.domain.bulletin.domain.NoticeResponse;
import com.domain.bulletin.domain.NoticeResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeFacade {

    private final NoticeService noticeService;
    private final MemberService memberService;

    public Notice findById(final Long noticeId) {
        return noticeService.findById(noticeId);
    }

    @Transactional
    public Message register(final NoticeCreate request) {
        Notice notice = noticeService.save(Notice.of(request));
        return Message.registerPostSuccess(Notice.class, notice.getId());
    }

    public NoticeResponse getSingleNotice(final Long noticeId) {
        Notice notice = findById(noticeId);
        return NoticeResponse.from(notice);
    }

    public NoticeResponses getNoticeList(final int pageNumber, final int pageSize) {
        return noticeService.getContain(pageNumber, pageSize);
    }

    public Message delete(final Long noticeId) {
        noticeService.deleteById(noticeId);
        return Message.deletePostSuccess(Notice.class, noticeId);
    }

    public Message edit(final Long noticeId, final NoticeEdit request, final Long memberId) {
        Member member = memberService.findById(memberId);
        validManager(member);
        noticeService.edit(noticeId, request);
        return Message.editPostSuccess(Notice.class, noticeId);
    }

    private void validManager(final Member member) {
        Role memberRole = member.getRole();
        if (!memberRole.equals(Role.MANAGER)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_DELETE_POST);
        }
    }
}
