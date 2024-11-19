package com.api.notification.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.notification.dto.response.NotificationFrontResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification", description = "알림 API")
@RequestMapping("/api/v1/notification")
public interface NotificationControllerDocs {

    @Operation(summary = "FCM Token 등록", description = "회원의 FCM Token을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "FCM Token 등록 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NotificationResponseConstant.UPDATE_FCM_TOKEN",
                                    description = "FCM Token이 성공적으로 등록되었습니다."
                            )))})
    @PatchMapping(value = "/fcm_token")
    TtoklipResponse<Message> updateMemberFCMToken(@Validated @RequestBody UpdateFCMTokenRequest request);

    @Operation(summary = "알림 조회", description = "회원의 알림 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NotificationResponseConstant.GET_NOTIFICATIONS",
                                    description = "알림 목록이 성공적으로 조회되었습니다."
                            )))})
    @GetMapping("/my-notification")
    TtoklipResponse<NotificationFrontResponses> getNotification(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page,

            @Parameter(description = "페이지당 개수 (기본값 5)", example = "5")
            @RequestParam(required = false, defaultValue = "5") int size);
}
