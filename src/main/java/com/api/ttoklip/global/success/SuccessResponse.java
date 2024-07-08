package com.api.ttoklip.global.success;

import static java.time.LocalDateTime.now;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Getter
@ToString
@JsonPropertyOrder({"time", "status", "code", "message", "result"})
public class SuccessResponse<T> {

    @JsonProperty("status")
    private int status;

    private LocalDateTime time;
    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public SuccessResponse(final T result) {
        this.result = result;
        this.status = HttpStatus.OK.value();
        this.time = now();
        this.code = SuccessResponseStatus.SUCCESS.getCode();
        this.message = SuccessResponseStatus.SUCCESS.getMessage();
    }

}
