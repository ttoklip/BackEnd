package com.api.notification.presentation;

import com.api.global.success.Message;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import com.api.notification.application.NotificationFacade;
import com.domain.notification.dto.response.NotificationFrontResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationFacade notificationFacade;

    @Operation(summary = "FCM Token 등록", description = "회원의 FCM Token을 등록합니다.")
    @PatchMapping(value = "/fcm_token")
    public SuccessResponse<Message> updateMemberFCMToken(
            @Valid @RequestBody final UpdateFCMTokenRequest request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = notificationFacade.updateMemberFCMToken(currentMemberId, request);
        return new SuccessResponse<>(message);
    }

    @GetMapping("/my-notification")
    public SuccessResponse<NotificationFrontResponses> getNotification(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page,
            @Parameter(description = "페이지당 개수 (기본값 5)", example = "0")
            @RequestParam(required = false, defaultValue = "5") final int size
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageRequest = PageRequest.of(page, size);
        NotificationFrontResponses response = notificationFacade.findNotification(currentMemberId, pageRequest);
        return new SuccessResponse<>(response);
    }

}
