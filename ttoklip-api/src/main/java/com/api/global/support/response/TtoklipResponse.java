package com.api.global.support.response;

import static java.time.LocalDateTime.now;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
@JsonPropertyOrder({"time", "status", "code", "message", "result"})
public class TtoklipResponse<T> {

    @JsonProperty("status")
    private int status;

    private LocalDateTime time;
    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    private TtoklipResponse(final T result, HttpStatus statusCode, String message) {
        this.result = result;
        this.status = statusCode.value();
        this.time = now();
        this.code = statusCode.toString();
        this.message = message;
    }

    public static <T> TtoklipResponse<T> ok(final T result) {
        return new TtoklipResponse<>(
                result,
                HttpStatus.OK,
                SuccessResponseStatus.SUCCESS.getMessage()
        );
    }

    public static <T> TtoklipResponse<T> created(final T result) {
        return new TtoklipResponse<>(
                result,
                HttpStatus.CREATED,
                SuccessResponseStatus.CREATED.getMessage()
        );
    }

    public static <T> TtoklipResponse<T> accepted(final T result) {
        return new TtoklipResponse<>(
                result,
                HttpStatus.ACCEPTED,
                SuccessResponseStatus.ACCEPTED.getMessage()
        );
    }
}
