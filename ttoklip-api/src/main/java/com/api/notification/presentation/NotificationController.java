package com.api.notification.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.notification.application.NotificationFacade;
import com.domain.notification.dto.response.NotificationFrontResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerDocs {

    private final NotificationFacade notificationFacade;

    @Override
    public TtoklipResponse<Message> updateMemberFCMToken(UpdateFCMTokenRequest request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = notificationFacade.updateMemberFCMToken(currentMemberId, request);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<NotificationFrontResponses> getNotification(int page, int size) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageRequest = PageRequest.of(page, size);
        NotificationFrontResponses response = notificationFacade.findNotification(currentMemberId, pageRequest);
        return new TtoklipResponse<>(response);
    }
}
