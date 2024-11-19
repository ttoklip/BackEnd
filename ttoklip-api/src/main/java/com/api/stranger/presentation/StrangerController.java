package com.api.stranger.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.stranger.application.StrangerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StrangerController implements StrangerControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final StrangerFacade strangerService;

    @Override
    public TtoklipResponse<StrangerResponse> getStrangerProfile(String nickname) {
        return new TtoklipResponse<>(strangerService.getStrangerProfile(nickname));
    }

    @Override
    public TtoklipResponse<HoneyTipPaging> strangerHoneyTip(int page, Long userId) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new TtoklipResponse<>(strangerService.strangerHoneyTip(pageable, userId));
    }
}
