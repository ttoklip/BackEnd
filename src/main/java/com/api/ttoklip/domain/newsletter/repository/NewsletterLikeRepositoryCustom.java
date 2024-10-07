package com.api.ttoklip.domain.newsletter.repository;

public interface NewsletterLikeRepositoryCustom {

    Long countNewsletterLikesByNewsletterId(final Long newsletterId);
}
