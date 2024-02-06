package com.api.ttoklip.domain.mypage.term.service;

import com.api.ttoklip.domain.mypage.noti.post.domain.Notice;
import com.api.ttoklip.domain.mypage.noti.post.domain.NoticeRepository;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeResponse;
import com.api.ttoklip.domain.mypage.noti.post.editor.NoticePostEditor;
import com.api.ttoklip.domain.mypage.term.domain.Term;
import com.api.ttoklip.domain.mypage.term.domain.TermRepository;
import com.api.ttoklip.domain.mypage.term.dto.request.TermCreateRequest;
import com.api.ttoklip.domain.mypage.term.dto.response.TermResponse;
import com.api.ttoklip.domain.mypage.term.editor.TermEditor;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {
    private final TermRepository termRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Term findTermById(final Long termId){
        return termRepository.findById(termId)
                .orElseThrow(()->new ApiException(ErrorType.TERM_NOT_FOUND));//notice 에러 추가 필요 SJ02.04
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final TermCreateRequest request){

        Term term = Term.of(request);
        termRepository.save(term);
        return term.getId();
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public TermResponse getSingleTerm(final Long termId) {//하나 조회
        Term term = findTermById(termId);
        TermResponse termResponse = TermResponse.of(term);//
        return termResponse;
    }
    public List<Term> getTermList() {//전체 조회
        //추후 구현
        return termRepository.findAll();
    }
    /* -------------------------------------------- DELETE  -------------------------------------------- */
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */

    /* -------------------------------------------- EDIT  -------------------------------------------- */
    @Transactional
    public Message edit(final Long termId, final TermCreateRequest request) {

        // 기존 게시글 찾기
        Term term = findTermById(termId);

        // ToDO Validate currentMember

        // title, content 수정
        TermEditor termEditor = getEditor(request, term);
        term.edit(termEditor);

        return Message.editPostSuccess(Term.class, term.getId());//message후에 추가
    }

    private TermEditor getEditor(final TermCreateRequest request, final Term term) {
        TermEditor.TermEditorBuilder editorBuilder = term.toEditor();
        TermEditor termEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return termEditor;
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */
}
