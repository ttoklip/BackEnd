package com.domain.honeytip.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.post.HoneyTipRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeHoneyTipPostRepository implements HoneyTipRepository {

    private final HashMap<Long, HoneyTip> honeyTipRepository = new HashMap<>();
    private Long honeyTipId = 0L;

    @Override
    public HoneyTip findByIdActivated(final Long honeyTipId) {
        HoneyTip honeyTip = honeyTipRepository.get(honeyTipId);
        if (honeyTip != null && !honeyTip.isDeleted()) {
            return honeyTip;
        }
        throw new ApiException(ErrorType.HONEY_TIP_NOT_FOUND);
    }

    @Override
    public HoneyTip findHoneyTipWithDetails(final Long honeyTipId) {
        HoneyTip honeyTip = honeyTipRepository.get(honeyTipId);
        if (honeyTip != null && !honeyTip.isDeleted()) {
            return honeyTip;
        }
        throw new ApiException(ErrorType.HONEY_TIP_NOT_FOUND);
    }

    @Override
    public Page<HoneyTip> matchCategoryPaging(final Category category, final Pageable pageable) {
        List<HoneyTip> filtered = honeyTipRepository.values().stream()
                .filter(honeyTip -> honeyTip.getCategory().equals(category) && !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        List<HoneyTip> pageContent = filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Override
    public List<HoneyTip> findRecent3() {
        return honeyTipRepository.values().stream()
                .filter(honeyTip -> !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId()))
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<HoneyTip> findHouseworkTips() {
        return honeyTipRepository.values().stream()
                .filter(honeyTip -> honeyTip.getCategory() == Category.HOUSEWORK && !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId())) // 최신순 정렬
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<HoneyTip> findRecipeTips() {
        return honeyTipRepository.values().stream()
                .filter(honeyTip -> honeyTip.getCategory() == Category.RECIPE && !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId())) // 최신순 정렬
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<HoneyTip> findSafeLivingTips() {
        return honeyTipRepository.values().stream()
                .filter(honeyTip -> honeyTip.getCategory() == Category.SAFE_LIVING && !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId())) // 최신순 정렬
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<HoneyTip> findWelfarePolicyTips() {
        return honeyTipRepository.values().stream()
                .filter(honeyTip -> honeyTip.getCategory() == Category.WELFARE_POLICY && !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId())) // 최신순 정렬
                .limit(10)
                .collect(Collectors.toList());
    }


    @Override
    public List<HoneyTip> getPopularityTop5() {
        return honeyTipRepository.values().stream()
                .filter(honeyTip -> !honeyTip.isDeleted())
                .sorted((h1, h2) -> {
                    int h1Score =
                            h1.getHoneyTipComments().size() + h1.getHoneyTipLikes().size() + h1.getHoneyTipScraps()
                                    .size();
                    int h2Score =
                            h2.getHoneyTipComments().size() + h2.getHoneyTipLikes().size() + h2.getHoneyTipScraps()
                                    .size();
                    return Integer.compare(h2Score, h1Score);
                })
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public HoneyTip save(final HoneyTip honeyTip) {
        honeyTipId++;
        HoneyTip savedHoneyTip = HoneyTip.builder()
                .id(honeyTipId)
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .category(honeyTip.getCategory())
                .honeyTipUrls(honeyTip.getHoneyTipUrls())
                .honeyTipImages(honeyTip.getHoneyTipImages())
                .honeyTipComments(honeyTip.getHoneyTipComments())
                .honeyTipScraps(honeyTip.getHoneyTipScraps())
                .honeyTipLikes(honeyTip.getHoneyTipLikes())
                .build();

        honeyTipRepository.put(honeyTipId, savedHoneyTip);

        return savedHoneyTip;
    }


}
