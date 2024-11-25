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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController implements NotificationControllerDocs {

    private final NotificationFacade notificationFacade;

    @Override
    @PatchMapping(value = "/fcm_token")
    public TtoklipResponse<Message> updateMemberFCMToken(@Validated @RequestBody UpdateFCMTokenRequest request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = notificationFacade.updateMemberFCMToken(currentMemberId, request);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/my-notification")
    public TtoklipResponse<NotificationFrontResponses> getNotification(@RequestParam(required = false, defaultValue = "0") int page,
                                                                       @RequestParam(required = false, defaultValue = "5") int size) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageRequest = PageRequest.of(page, size);
        NotificationFrontResponses response = notificationFacade.findNotification(currentMemberId, pageRequest);
        return new TtoklipResponse<>(response);
    }
}
