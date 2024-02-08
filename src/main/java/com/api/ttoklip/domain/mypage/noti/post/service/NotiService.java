package com.api.ttoklip.domain.mypage.noti.post.service;

import com.api.ttoklip.domain.mypage.noti.post.domain.NoticeDefaultRepository;
import com.api.ttoklip.domain.mypage.noti.post.domain.Notice;
import com.api.ttoklip.domain.mypage.noti.post.domain.NoticePagingRepository;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeEditRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticePaging;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeResponse;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeSingleResponse;
import com.api.ttoklip.domain.mypage.noti.post.editor.NoticePostEditor;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotiService {

    private final NoticeDefaultRepository noticeDefaultRepository;
    private final NoticePagingRepository noticePagingRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Notice findNoticeById(final Long noticeId){
        Notice notice= noticeDefaultRepository.findById(noticeId)
                .orElseThrow(()->new ApiException(ErrorType.NOTICE_NOT_FOUND));
        if(notice.isDeleted()==true){
            throw new ApiException(ErrorType.NOTICE_NOT_FOUND); //notice 에러 추가 필요 SJ02.04
        }
        return notice;
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final NoticeRequest request){

        Notice notice=Notice.of(request);
        noticeDefaultRepository.save(notice);
        Long noticeId = notice.getId();
        return Message.registerPostSuccess(Notice.class,noticeId);
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public NoticeResponse getSingleNotice(final Long noticeId) {//하나 조회
        Notice notice=findNoticeById(noticeId);
        NoticeResponse noticeResponse = NoticeResponse.of(notice);//
        return noticeResponse;
    }
    public NoticePaging getNoticeList(final Pageable pageable) {//전체 조회
        Page<Notice> contentPaging=noticePagingRepository.getContain(pageable);
        List<Notice> contents=contentPaging.getContent();
        List<NoticeSingleResponse>noticeSingleData = contents.stream()
                .map(NoticeSingleResponse::noticeFrom)
                .toList();
        //추후 구현 02.08
        //return noticeDefaultRepository.findAll();
        return NoticePaging.builder()
                .notices(noticeSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
    /* -------------------------------------------- DELETE  -------------------------------------------- */
    @Transactional
    public Message deleteNotice(final Long noticeId){//소프트삭제 구현
        Notice notice = findNoticeById(noticeId);
        notice.deactivate();
        noticeDefaultRepository.save(notice);
        return Message.deletePostSuccess(Notice.class, noticeId);
    }
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */

    /* -------------------------------------------- EDIT  -------------------------------------------- */
    @Transactional
    public Message edit(final Long noticeId, final NoticeEditRequest request) {

        // 기존 게시글 찾기
        Notice notice = findNoticeById(noticeId);

        // ToDO Validate currentMember

        // title, content 수정
        NoticePostEditor noticePostEditor = getPostEditor(request, notice);
        notice.edit(noticePostEditor);

        return Message.editPostSuccess(Notice.class, notice.getId());//message후에 추가
    }

    private NoticePostEditor getPostEditor(final NoticeEditRequest request, final Notice notice) {
        NoticePostEditor.NoticePostEditorBuilder editorBuilder = notice.toEditor();
        NoticePostEditor noticePostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return noticePostEditor;
    }

    /* -------------------------------------------- EDIT  -------------------------------------------- */
}
