package com.api.ttoklip.domain.mypage.term.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.mypage.term.domain.Term;
import com.api.ttoklip.domain.mypage.term.domain.TermAgreement;
import com.api.ttoklip.domain.mypage.term.domain.TermPaginRepository;
import com.api.ttoklip.domain.mypage.term.repository.TermAgreementRepository;
import com.api.ttoklip.domain.mypage.term.repository.TermRepository;
import com.api.ttoklip.domain.mypage.term.dto.request.TermCreateRequest;
import com.api.ttoklip.domain.mypage.term.dto.request.TermEditRequest;
import com.api.ttoklip.domain.mypage.term.dto.response.TermPaging;
import com.api.ttoklip.domain.mypage.term.dto.response.TermResponse;
import com.api.ttoklip.domain.mypage.term.dto.response.TermSingleResponse;
import com.api.ttoklip.domain.mypage.term.editor.TermEditor;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {
    private final TermRepository termRepository;
    private final TermPaginRepository termPaginRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Term findTermById(final Long termId) {
        return termRepository.findById(termId)
                .orElseThrow(() -> new ApiException(ErrorType.TERM_NOT_FOUND));//notice 에러 추가 필요 SJ02.04
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final TermCreateRequest request) {

        Term term = Term.of(request);
        termRepository.save(term);
        Long termId = term.getId();

        return Message.registerPostSuccess(Term.class, termId);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public TermResponse getSingleTerm(final Long termId) {//하나 조회
        Term term = findTermById(termId);
        TermResponse termResponse = TermResponse.of(term);//
        return termResponse;
    }

    public TermPaging getTermList(final Pageable pageable) {//전체 조회
        //추후 구현
        Page<Term> contentPaging = termPaginRepository.getContain(pageable);
        List<Term> contents = contentPaging.getContent();
        List<TermSingleResponse> termSingleData = contents.stream()
                .map(TermSingleResponse::termFrom)
                .toList();
        return TermPaging.builder()
                .terms(termSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
    /* -------------------------------------------- DELETE  -------------------------------------------- */
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */

    /* -------------------------------------------- EDIT  -------------------------------------------- */
    @Transactional
    public Message edit(final Long termId, final TermEditRequest request) {

        // 기존 게시글 찾기
        Term term = findTermById(termId);

        // ToDO Validate currentMember

        // title, content 수정
        TermEditor termEditor = getEditor(request, term);
        term.edit(termEditor);

        return Message.editPostSuccess(Term.class, term.getId());
    }

    private TermEditor getEditor(final TermEditRequest request, final Term term) {
        TermEditor.TermEditorBuilder editorBuilder = term.toEditor();
        TermEditor termEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return termEditor;
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */
    
}
