package member.fixture;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.privacy.domain.Profile;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    public static Member 일반_회원_생성1() {
        Profile profile = Profile.builder()
                .profileImgUrl("https://default-image-url.com/default.png")
                .build();

        Member member = Member.builder()
                .id(897354L)
                .originName("origin")
                .email("normal@test.com")
                .provider("local")
                .nickname("일반 회원")
                .fcmToken("fcmToken123")
                .independentMonth(5)
                .independentYear(2)
                .street("서울시 강남구")
                .role(Role.CLIENT)
                .password("password#123")
                .build();

        member.linkProfile(profile);

        return member;
    }

    public static Member 일반_회원_생성2() {
        Profile profile = Profile.builder()
                .profileImgUrl("https://default-image-url.com/default.png")
                .build();

        Member member = Member.builder()
                .id(12341234L)
                .originName("origin2")
                .email("normal2@test.com")
                .provider("local2")
                .nickname("일반 회원2")
                .fcmToken("fcmToken1234")
                .independentMonth(3)
                .independentYear(1)
                .street("서울시 도봉구")
                .role(Role.CLIENT)
                .password("password#1234")
                .build();

        member.linkProfile(profile);

        return member;
    }

    public static Member 관리자_계정_생성() {
        return Member.builder()
                .originName("adminOrigin")
                .email("admin@test.com")
                .provider("local")
                .nickname("관리자 계정")
                .fcmToken("fcmToken456")
                .independentMonth(3)
                .independentYear(1)
                .street("서울시 송파구")
                .role(Role.MANAGER)
                .password("password#456")
                .build();
    }
}
