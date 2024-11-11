package com.domain.newsletter.application;

import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.TodayNewsletter;
import com.domain.newsletter.domain.TodayNewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodayNewsletterService {

    private final TodayNewsletterRepository todayNewsletterRepository;

    public void save(final Newsletter newsletter) {
        TodayNewsletter todayNewsletter = TodayNewsletter.from(newsletter);
        todayNewsletterRepository.save(todayNewsletter);
    }
}
