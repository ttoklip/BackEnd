package com.api.ttoklip.domain.newsletter.repository.post;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterRepositoryImpl implements NewsletterRepository {

    private final NewsletterJpaRepository jpaRepository;
    private final NewsletterQueryRepository queryDslRepository;

    @Override
    public Newsletter findByIdActivated(final Long newsletterId) {
        return queryDslRepository.findByIdActivated(newsletterId);
    }

    @Override
    public Newsletter findByIdFetchJoin(final Long postId) {
        return queryDslRepository.findByIdFetchJoin(postId);
    }

    @Override
    public Long findNewsletterCount() {
        return queryDslRepository.findNewsletterCount();
    }

    @Override
    public Page<Newsletter> getPaging(final Category category, final Pageable pageable) {
        return queryDslRepository.getPaging(category, pageable);
    }

    @Override
    public List<Newsletter> getRecent3() {
        return queryDslRepository.getRecent3();
    }

    @Override
    public List<Newsletter> getHouseWorkNewsletter10Desc() {
        return queryDslRepository.getHouseWorkNewsletter10Desc();
    }

    @Override
    public List<Newsletter> getRecipeNewsletter10Desc() {
        return queryDslRepository.getRecipeNewsletter10Desc();
    }

    @Override
    public List<Newsletter> getSafeLivingNewsletter10Desc() {
        return queryDslRepository.getSafeLivingNewsletter10Desc();
    }

    @Override
    public List<Newsletter> getWelfarePolicyNewsletter10Desc() {
        return queryDslRepository.getWelfarePolicyNewsletter10Desc();
    }

    @Override
    public List<Newsletter> findRandom4ActiveNewsletters() {
        return queryDslRepository.findRandom4ActiveNewsletters();
    }

    @Override
    public Page<Newsletter> getContain(final String keyword, final Pageable pageable, final String sort) {
        return queryDslRepository.getContain(keyword, pageable, sort);
    }

    @Override
    public Newsletter save(final Newsletter newsletter) {
        return jpaRepository.saveAndFlush(newsletter);
    }
}
