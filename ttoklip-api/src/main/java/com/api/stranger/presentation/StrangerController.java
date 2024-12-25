package com.api.stranger.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.stranger.application.StrangerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stranger")
public class StrangerController implements StrangerControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final StrangerFacade strangerService;

    @Override
    @GetMapping
    public TtoklipResponse<StrangerResponse> getStrangerProfile(
            final @RequestParam String nickname
    ) {
        return TtoklipResponse.ok(
                strangerService.getStrangerProfile(nickname)
        );
    }

    @Override
    @GetMapping("/honeytip/{userId}")
    public TtoklipResponse<HoneyTipPaging> strangerHoneyTip(
            final @RequestParam(defaultValue = "0") int page,
            final @PathVariable Long userId
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                strangerService.strangerHoneyTip(pageable, userId)
        );
    }
}
