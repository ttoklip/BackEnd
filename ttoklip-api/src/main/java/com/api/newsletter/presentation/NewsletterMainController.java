package com.api.newsletter.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.newsletter.application.NewsletterMainFacade;
import com.api.newsletter.presentation.response.NewsletterMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/newsletters/posts")
public class NewsletterMainController implements NewsletterMainControllerDocs {

    private final NewsletterMainFacade newsletterMainFacade;

    @Override
    @GetMapping
    public TtoklipResponse<NewsletterMainResponse> category() {
        return new TtoklipResponse<>(newsletterMainFacade.getMainData());
    }
}
