package com.api.ttoklip.domain.newsletter.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsletterQueryDslRepository {

    Newsletter findByIdActivated(final Long newsletterId);

    Newsletter findByIdFetchJoin(final Long postId);

    Long findNewsletterCount();

    Page<Newsletter> getPaging(final Category category, final Pageable pageable);

    List<Newsletter> getRecent3();

    List<Newsletter> getHouseWorkNewsletter10Desc();

    List<Newsletter> getRecipeNewsletter10Desc();

    List<Newsletter> getSafeLivingNewsletter10Desc();

    List<Newsletter> getWelfarePolicyNewsletter10Desc();

    List<Newsletter> findRandom4ActiveNewsletters();

    Page<Newsletter> getContain(final String keyword, final Pageable pageable, final String sort);
}
