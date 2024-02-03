package com.api.ttoklip.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverProfile {

    @JsonProperty("response")
    private NaverResponse naverResponse;

    @Data
    public static class NaverResponse {
        private String id;
        private String name;
        private String email;
    }
}
