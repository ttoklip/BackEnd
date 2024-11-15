package com.domain.newseltter.reository;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeNewsletterPostRepository implements NewsletterRepository {

    private final Map<Long, Newsletter> memoryRepository = new HashMap<>();
    private Long idCounter = 1L;

    @Override
    public Newsletter findByIdActivated(Long newsletterId) {
        return Optional.ofNullable(memoryRepository.get(newsletterId))
                .filter(newsletter -> !newsletter.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public Newsletter findByIdFetchJoin(Long postId) {
        return Optional.ofNullable(memoryRepository.get(postId))
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public Long findNewsletterCount() {
        return (long) memoryRepository.size();
    }

    @Override
    public Page<Newsletter> getPaging(Category category, Pageable pageable) {
        List<Newsletter> filteredNewsletters = memoryRepository.values().stream()
                .filter(newsletter -> newsletter.getCategory() == category && !newsletter.isDeleted())
                .sorted(Comparator.comparingLong(Newsletter::getId).reversed()) // 최신순 정렬
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredNewsletters.size());

        return new PageImpl<>(filteredNewsletters.subList(start, end), pageable, filteredNewsletters.size());
    }

    @Override
    public List<Newsletter> getRecent3() {
        return memoryRepository.values().stream()
                .filter(newsletter -> !newsletter.isDeleted())
                .sorted(Comparator.comparingLong(Newsletter::getId).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<Newsletter> getHouseWorkNewsletter10Desc() {
        return getNewsletterByCategory(Category.HOUSEWORK, 10);
    }

    @Override
    public List<Newsletter> getRecipeNewsletter10Desc() {
        return getNewsletterByCategory(Category.RECIPE, 10);
    }

    @Override
    public List<Newsletter> getSafeLivingNewsletter10Desc() {
        return getNewsletterByCategory(Category.SAFE_LIVING, 10);
    }

    @Override
    public List<Newsletter> getWelfarePolicyNewsletter10Desc() {
        return getNewsletterByCategory(Category.WELFARE_POLICY, 10);
    }

    @Override
    public List<Newsletter> findRandomActiveNewsletters(final int pageSize) {
        List<Newsletter> activeNewsletters = memoryRepository.values().stream()
                .filter(newsletter -> !newsletter.isDeleted())
                .toList();

        if (activeNewsletters.isEmpty()) {
            throw new ApiException(ErrorType.NEWSLETTER_NOT_FOUND);
        }

        Collections.shuffle(activeNewsletters);

        return activeNewsletters.stream()
                .limit(pageSize)
                .collect(Collectors.toList());
    }


    @Override
    public Page<Newsletter> getContain(String keyword, Pageable pageable, String sort) {
        List<Newsletter> filteredNewsletters = memoryRepository.values().stream()
                .filter(newsletter -> (newsletter.getTitle().contains(keyword) || newsletter.getContent().contains(keyword)) && !newsletter.isDeleted())
                .collect(Collectors.toList());

        if (filteredNewsletters.isEmpty()) {
            throw new ApiException(ErrorType.NEWSLETTER_NOT_FOUND);
        }

        if (sort.equals("popularity")) {
            filteredNewsletters.sort(Comparator.comparingLong(this::calculatePopularity).reversed());
        } else if (sort.equals("latest")) {
            filteredNewsletters.sort(Comparator.comparingLong(Newsletter::getId).reversed());
        } else {
            throw new ApiException(ErrorType.INVALID_SORT_TYPE);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredNewsletters.size());

        return new PageImpl<>(filteredNewsletters.subList(start, end), pageable, filteredNewsletters.size());
    }

    @Override
    public Newsletter save(Newsletter newsletter) {
        Newsletter savedNewsletter = Newsletter.builder()
                .id(idCounter)
                .title(newsletter.getTitle())
                .content(newsletter.getContent())
                .category(newsletter.getCategory())
                .mainImageUrl(newsletter.getMainImageUrl())
                .member(newsletter.getMember())
                .newsletterImages(newsletter.getNewsletterImages())
                .newsletterLikes(newsletter.getNewsletterLikes())
                .newsletterScraps(newsletter.getNewsletterScraps())
                .newsletterUrlList(newsletter.getNewsletterUrlList())
                .newsletterComments(newsletter.getNewsletterComments())
                .build();

        memoryRepository.put(savedNewsletter.getId(), savedNewsletter);

        idCounter++;
        return savedNewsletter;
    }

    private List<Newsletter> getNewsletterByCategory(Category category, int limit) {
        return memoryRepository.values().stream()
                .filter(newsletter -> newsletter.getCategory() == category && !newsletter.isDeleted())
                .sorted(Comparator.comparingLong(Newsletter::getId).reversed()) // 최신순 정렬
                .limit(limit)
                .collect(Collectors.toList());
    }

    private long calculatePopularity(Newsletter newsletter) {
        return newsletter.getNewsletterLikes().size() + newsletter.getNewsletterComments().size()
                + newsletter.getNewsletterScraps().size();
    }
}
