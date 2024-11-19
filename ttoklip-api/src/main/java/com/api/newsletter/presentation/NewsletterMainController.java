package com.api.newsletter.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.newsletter.application.NewsletterMainFacade;
import com.api.newsletter.presentation.response.NewsletterMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewsletterMainController implements NewsletterMainControllerDocs {

    private final NewsletterMainFacade newsletterMainFacade;

    @Override
    public TtoklipResponse<NewsletterMainResponse> category() {
        return new TtoklipResponse<>(newsletterMainFacade.getMainData());
    }
}
