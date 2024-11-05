package com.api.ttoklip.domain.bulletin.service;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import com.api.ttoklip.domain.bulletin.domain.NoticeRepository;
import com.api.ttoklip.domain.bulletin.dto.request.NoticeEditRequest;
import com.api.ttoklip.domain.bulletin.editor.NoticePostEditor;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.vo.Role;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Notice findById(final Long noticeId) {
        return noticeRepository.findByIdActivated(noticeId);
    }

    public Page<Notice> getContain(final Pageable pageable) {
        return noticeRepository.getContain(pageable);
    }

    @Transactional
    public void deleteById(final Long noticeId) {
        Notice notice = findById(noticeId);
        notice.deactivate();
    }

    @Transactional
    public void edit(final Long noticeId, final NoticeEditRequest request, final Member member) {
        Notice notice = findById(noticeId);
        validManager(member);
        NoticePostEditor noticePostEditor = getPostEditor(request, notice);
        notice.edit(noticePostEditor);
    }

    private void validManager(final Member member) {
        Role memberRole = member.getRole();
        if (!memberRole.equals(Role.MANAGER)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_DELETE_POST);
        }
    }

    private NoticePostEditor getPostEditor(final NoticeEditRequest request, final Notice notice) {
        return notice.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    @Transactional
    public Notice save(final Notice notice) {
        return noticeRepository.save(notice);
    }
}
