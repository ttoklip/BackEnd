package com.domain.newsletter.domain;

import com.domain.common.vo.Category;
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

    List<Newsletter> findRandomActiveNewsletters(final int pageSize);

    Page<Newsletter> getContain(String keyword, Pageable pageable, String sort);

    Newsletter save(Newsletter newsletter);
}
