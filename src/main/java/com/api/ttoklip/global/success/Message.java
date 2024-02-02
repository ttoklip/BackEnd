package com.api.ttoklip.global.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

    @Schema(type = "string", example = "메시지 문구를 출력합니다.", description = "메시지 입니다.")
    private String message;

    public static Message from(final String message) {
        return Message.builder()
                .message(message)
                .build();
    }

}
