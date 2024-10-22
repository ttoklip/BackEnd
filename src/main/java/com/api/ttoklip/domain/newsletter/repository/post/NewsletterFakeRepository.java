package com.api.ttoklip.domain.newsletter.repository.post;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public class NewsletterFakeRepository implements NewsletterRepository {

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
                .sorted(Comparator.comparingLong(Newsletter::getId).reversed()) // 최신순 정렬
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
    public List<Newsletter> findRandom4ActiveNewsletters() {
        List<Newsletter> activeNewsletters = memoryRepository.values().stream()
                .filter(newsletter -> !newsletter.isDeleted())
                .collect(Collectors.toList());

        if (activeNewsletters.size() < 4) {
            throw new ApiException(ErrorType.NEWSLETTER_NOT_FOUND); // 최소 4개의 뉴스레터가 없으면 예외 발생
        }

        Collections.shuffle(activeNewsletters);

        return activeNewsletters.stream()
                .limit(4)
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
        idCounter++;
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
