package com.api.ttoklip.domain.manager.inquiry.service;

import com.api.ttoklip.domain.manager.inquiry.domain.Inquiry;
import com.api.ttoklip.domain.manager.inquiry.domain.InquiryRepository;
import com.api.ttoklip.domain.manager.inquiry.dto.request.InquiryCreateRequest;
import com.api.ttoklip.domain.manager.inquiry.dto.response.InquiryResponse;
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
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Inquiry findInquiryById(final Long noticeId){
        return inquiryRepository.findById(noticeId)
                .orElseThrow(()->new ApiException(ErrorType.INQUIRY_NOT_FOUND));//notice 에러 추가 필요 SJ02.04
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final InquiryCreateRequest request){

        Inquiry inquiry=Inquiry.of(request);
        inquiryRepository.save(inquiry);
        return inquiry.getId();
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public InquiryResponse getSingleInquiry(final Long inquiryId) {//하나 조회
        Inquiry inquiry=findInquiryById(inquiryId);
        InquiryResponse inquiryResponse = InquiryResponse.of(inquiry);//
        return inquiryResponse;
    }
    public List<Inquiry> getInquiryList() {//전체 조회
        //추후 구현
        return inquiryRepository.findAll();
    }
    /* -------------------------------------------- DELETE  -------------------------------------------- */
    @Transactional
    public Message deleteInquiry(final Long inquiryId){//소프트삭제 구현
        Inquiry inquiry = findInquiryById(inquiryId);
        inquiry.deactivate();

        return Message.deletePostSuccess(Inquiry.class, inquiryId);
    }
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */
}
