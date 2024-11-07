package com.api.ttoklip.domain.bulletin.facade;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import com.api.ttoklip.domain.bulletin.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.bulletin.dto.request.NoticeEditRequest;
import com.api.ttoklip.domain.bulletin.dto.response.NoticePaging;
import com.api.ttoklip.domain.bulletin.dto.response.NoticeResponse;
import com.api.ttoklip.domain.bulletin.dto.response.NoticeSingleResponse;
import com.api.ttoklip.domain.bulletin.service.NoticeService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NoticeFacade {

    private final NoticeService noticeService;
    private final MemberService memberService;

    public Notice findById(final Long noticeId) {
        return noticeService.findById(noticeId);
    }

    @Transactional
    public Message register(final NoticeCreateRequest request) {
        Notice notice = noticeService.save(Notice.of(request));
        return Message.registerPostSuccess(Notice.class, notice.getId());
    }

    public NoticeResponse getSingleNotice(final Long noticeId) {
        Notice notice = findById(noticeId);
        return NoticeResponse.of(notice);
    }

    public NoticePaging getNoticeList(final Pageable pageable) {
        Page<Notice> paging = noticeService.getContain(pageable);
        List<Notice> contents = paging.getContent();
        List<NoticeSingleResponse> noticeResponses = contents.stream()
                .map(NoticeSingleResponse::noticeFrom)
                .toList();

        return NoticePaging.of(noticeResponses, paging.getTotalPages(), paging.getTotalElements(), paging.isFirst(),
                paging.isLast());
    }

    public Message delete(final Long noticeId) {
        noticeService.deleteById(noticeId);
        return Message.deletePostSuccess(Notice.class, noticeId);
    }

    public Message edit(final Long noticeId, final NoticeEditRequest request, final Long memberId) {
        Member member = memberService.findById(memberId);
        noticeService.edit(noticeId, request, member);
        return Message.editPostSuccess(Notice.class, noticeId);
    }

}
