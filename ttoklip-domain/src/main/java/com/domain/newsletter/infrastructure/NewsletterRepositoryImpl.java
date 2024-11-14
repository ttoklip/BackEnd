package com.domain.newsletter.infrastructure;

import com.domain.common.vo.Category;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterRepositoryImpl implements NewsletterRepository {

    private final NewsletterJpaRepository jpaRepository;
//    private final NewsletterQueryRepository queryDslRepository;

    @Override
    public Newsletter findByIdActivated(final Long newsletterId) {
//        return queryDslRepository.findByIdActivated(newsletterId);
        return null;
    }

    @Override
    public Newsletter findByIdFetchJoin(final Long postId) {
//        return queryDslRepository.findByIdFetchJoin(postId);
        return null;
    }

    @Override
    public Long findNewsletterCount() {
        return null;
    }

    @Override
    public Page<Newsletter> getPaging(final Category category, final Pageable pageable) {
//        return queryDslRepository.getPaging(category, pageable);
        return null;
    }

    @Override
    public List<Newsletter> getRecent3() {
//        return queryDslRepository.getRecent3();
        return null;
    }

    @Override
    public List<Newsletter> getHouseWorkNewsletter10Desc() {
//        return queryDslRepository.getHouseWorkNewsletter10Desc();
        return null;
    }

    @Override
    public List<Newsletter> getRecipeNewsletter10Desc() {
//        return queryDslRepository.getRecipeNewsletter10Desc();
        return null;
    }

    @Override
    public List<Newsletter> getSafeLivingNewsletter10Desc() {
//        return queryDslRepository.getSafeLivingNewsletter10Desc();
        return null;
    }

    @Override
    public List<Newsletter> getWelfarePolicyNewsletter10Desc() {
//        return queryDslRepository.getWelfarePolicyNewsletter10Desc();
        return null;
    }

    @Override
    public List<Newsletter> findRandomActiveNewsletters(final int pageSize) {
//        return queryDslRepository.findRandomActiveNewsletters(pageSize);
        return null;
    }

    @Override
    public Page<Newsletter> getContain(final String keyword, final Pageable pageable, final String sort) {
//        return queryDslRepository.getContain(keyword, pageable, sort);
        return null;
    }

    @Override
    public Newsletter save(final Newsletter newsletter) {
        return jpaRepository.saveAndFlush(newsletter);
    }
}
