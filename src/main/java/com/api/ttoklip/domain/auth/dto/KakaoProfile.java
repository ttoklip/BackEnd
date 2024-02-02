package com.api.ttoklip.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoProfile {

    private long id;

    @JsonProperty("connected_at")
    private String connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;



    @Data
    public class KakaoAccount {
        @JsonProperty("has_email")
        private boolean hasEmail;
        @JsonProperty("email_needs_agreement")
        private boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        private boolean isEmailValid;
        @JsonProperty("is_email_verified")
        private boolean isEmailVerified;
        private String email;
        private Profile profile;

        @Data
        public static class Profile {
            private String nickname;
        }
    }
}
