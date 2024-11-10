package com.api.ttoklip.domain.newsletter.repository.post;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsletterRepository {

    Newsletter findByIdActivated(Long newsletterId);

    Newsletter findByIdFetchJoin(Long postId);

    Long findNewsletterCount();

    Page<Newsletter> getPaging(Category category, Pageable pageable);

    List<Newsletter> getRecent3();

    List<Newsletter> getHouseWorkNewsletter10Desc();

    List<Newsletter> getRecipeNewsletter10Desc();

    List<Newsletter> getSafeLivingNewsletter10Desc();

    List<Newsletter> getWelfarePolicyNewsletter10Desc();

    List<Newsletter> findRandom4ActiveNewsletters();

    Page<Newsletter> getContain(String keyword, Pageable pageable, String sort);

    Newsletter save(Newsletter newsletter);
}
