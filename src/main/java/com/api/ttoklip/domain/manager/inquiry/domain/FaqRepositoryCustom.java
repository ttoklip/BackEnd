package com.api.ttoklip.domain.manager.inquiry.domain;

public interface FaqRepositoryCustom {
    Faq findByIdActivated(final Long faqId);
}
