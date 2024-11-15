package com.domain.bulletin.application;

import com.domain.bulletin.domain.Notice;
import com.domain.bulletin.domain.NoticeEdit;
import com.domain.bulletin.domain.NoticeEditor;
import com.domain.bulletin.domain.NoticeRepository;
import com.domain.bulletin.domain.NoticeResponses;
import lombok.RequiredArgsConstructor;
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

    public NoticeResponses getContain(final int pageNumber, final int pageSize) {
        return noticeRepository.getContain(pageNumber, pageSize);
    }

    @Transactional
    public void deleteById(final Long noticeId) {
        Notice notice = findById(noticeId);
        notice.deactivate();
    }

    @Transactional
    public void edit(final Long noticeId, final NoticeEdit request) {
        Notice notice = findById(noticeId);
        NoticeEditor noticePostEditor = getEditor(request, notice);
        notice.edit(noticePostEditor);
    }

    private NoticeEditor getEditor(final NoticeEdit request, final Notice notice) {
        return notice.toEditor()
                .title(request.title())
                .content(request.content())
                .build();
    }

    @Transactional
    public Notice save(final Notice notice) {
        return noticeRepository.save(notice);
    }
}
