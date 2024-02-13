package com.api.ttoklip.domain.manager.inquiry.service;

import com.api.ttoklip.domain.manager.inquiry.domain.*;
import com.api.ttoklip.domain.manager.inquiry.dto.request.FaqCreateRequest;
import com.api.ttoklip.domain.manager.inquiry.dto.request.InquiryCreateRequest;
import com.api.ttoklip.domain.manager.inquiry.dto.response.*;
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
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryPagingRepository inquiryPagingRepository;
    private final FaqRepository faqRepository;
    private final FaqPagingRepository faqPagingRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Inquiry findInquiryById(final Long inquiryId) {
        return inquiryRepository.findByIdActivated(inquiryId);
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final InquiryCreateRequest request) {
        Inquiry inquiry = Inquiry.of(request);
        inquiryRepository.save(inquiry);
        Long inquiryId = inquiry.getId();
        return Message.registerPostSuccess(Inquiry.class, inquiryId);
    }
    @Transactional
    public Message faqRegister(final FaqCreateRequest request) {
        Faq faq = Faq.of(request);
        faqRepository.save(faq);
        Long faqId = faq.getId();
        return Message.registerPostSuccess(Faq.class, faqId);
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public InquiryResponse getSingleInquiry(final Long inquiryId) {//하나 조회
        Inquiry inquiry = findInquiryById(inquiryId);
        InquiryResponse inquiryResponse = InquiryResponse.of(inquiry);//
        return inquiryResponse;
    }

    public InquiryPaging getInquiryList(final Pageable pageable) {//전체 조회
        Page<Inquiry> contentPaging = inquiryPagingRepository.getContain(pageable);
        List<Inquiry> contents = contentPaging.getContent();
        List<InquirySingleResponse> inquirySingleData = contents.stream()
                .map(InquirySingleResponse::inquiryFrom)
                .toList();
        //추후 구현 02.08
        //return noticeDefaultRepository.findAll();
        return InquiryPaging.builder()
                .inquiries(inquirySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public FaqPaging getFaqList(final Pageable pageable){
        Page<Faq> contentPaging = faqPagingRepository.getContain(pageable);
        List<Faq> contents = contentPaging.getContent();
        List<FaqSingleResponse> faqSingleData = contents.stream()
                .map(FaqSingleResponse::faqFrom)
                .toList();
        //추후 구현 02.08
        //return noticeDefaultRepository.findAll();
        return FaqPaging.builder()
                .faqs(faqSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    /* -------------------------------------------- DELETE  -------------------------------------------- */
    @Transactional
    public Message deleteInquiry(final Long inquiryId) {//소프트삭제 구현
        Inquiry inquiry = findInquiryById(inquiryId);
        inquiry.deactivate();

        return Message.deletePostSuccess(Inquiry.class, inquiryId);
    }
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */
}
