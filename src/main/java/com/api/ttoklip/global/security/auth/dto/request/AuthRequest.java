package com.api.ttoklip.global.security.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@ToString
public class AuthRequest {

    @Schema(description = "직접 로그인 ID", example = "ttok123@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "asdf1234!")
    private String password;

    @Schema(description = "이름", example = "민희진")
    private String originName;

    @Schema(description = "닉네임", example = "별명별명")
    private String nickname;

    @Schema(description = "독립 경력(년)", example = "2")
    private int independentYear;

    @Schema(description = "독립 경력(월)", example = "6")
    private int independentMonth;

    @Schema(description = "주소", example = "경기도 안산시 상록구")
    private String street;

    private List<String> categories;

    private MultipartFile profileImage;
}
