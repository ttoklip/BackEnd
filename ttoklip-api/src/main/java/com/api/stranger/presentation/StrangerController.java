package com.api.stranger.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.stranger.application.StrangerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stranger")
public class StrangerController implements StrangerControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final StrangerFacade strangerService;

    @Override
    @GetMapping
    public TtoklipResponse<StrangerResponse> getStrangerProfile(@RequestParam String nickname) {
        return new TtoklipResponse<>(strangerService.getStrangerProfile(nickname));
    }

    @Override
    @GetMapping("/honeytip/{userId}")
    public TtoklipResponse<HoneyTipPaging> strangerHoneyTip(@RequestParam(defaultValue = "0") int page,
                                                            @PathVariable Long userId) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new TtoklipResponse<>(strangerService.strangerHoneyTip(pageable, userId));
    }
}
