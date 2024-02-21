package com.api.ttoklip.domain.manager.inquiry.domain;

public interface InquiryRepositoryCustom {
    Inquiry findByIdActivated(final Long inquiryId);
}
