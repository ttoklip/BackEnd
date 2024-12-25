package com.api.notification.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.notification.application.NotificationFacade;
import com.domain.notification.dto.response.NotificationFrontResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController implements NotificationControllerDocs {

    private final NotificationFacade notificationFacade;

    @Override
    @PatchMapping(value = "/fcm_token")
    public TtoklipResponse<Message> updateMemberFCMToken(
            final @Validated @RequestBody UpdateFCMTokenRequest request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                notificationFacade.updateMemberFCMToken(currentMemberId, request)
        );
    }

    @Override
    @GetMapping("/my-notification")
    public TtoklipResponse<NotificationFrontResponses> getNotification(
            final @RequestParam(required = false, defaultValue = "0") int page,
            final @RequestParam(required = false, defaultValue = "5") int size
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageRequest = PageRequest.of(page, size);
        return TtoklipResponse.ok(
                notificationFacade.findNotification(currentMemberId, pageRequest)
        );
    }
}
