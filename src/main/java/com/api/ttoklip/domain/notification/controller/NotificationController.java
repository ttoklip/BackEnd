package com.api.ttoklip.domain.notification.controller;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.notification.dto.request.UpdateFCMTokenRequest;
import com.api.ttoklip.domain.notification.dto.response.NotificationFrontResponses;
import com.api.ttoklip.domain.notification.service.NotificationService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final MemberService memberService;
    private final NotificationService notificationService;

    @Operation(summary = "FCM Token 등록", description = "회원의 FCM Token을 등록합니다.")
    @PatchMapping(value = "/fcm_token")
    public SuccessResponse<Message> updateMemberFCMToken(
            @Valid @RequestBody final UpdateFCMTokenRequest request
    ) {
        return new SuccessResponse<>(
                memberService.updateMemberFCMToken(getCurrentMember(), request.fcmToken())
        );
    }

    @GetMapping("/my-notification")
    public SuccessResponse<NotificationFrontResponses> getNotification(
            @RequestParam final String notificationCategory
    ) {
        return new SuccessResponse<>(
                notificationService.findNotificationByCategory(notificationCategory)
        );
    }

}
