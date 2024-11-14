package member.fixture;

import com.domain.member.domain.Member;
import com.domain.member.domain.vo.Provider;
import com.domain.member.domain.vo.Role;
import com.domain.profile.domain.Profile;
import java.util.List;

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
                .provider(Provider.LOCAL)
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
                .provider(Provider.KAKAO)
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

    public static Member 일반_회원_생성3() {
        Profile profile = Profile.builder()
                .profileImgUrl("https://default-image-url.com/default.png")
                .build();

        Member member = Member.builder()
                .id(125423L)
                .originName("origin3")
                .email("normal3@test.com")
                .provider(Provider.NAVER)
                .nickname("일반 회원3")
                .fcmToken("fcmToken12345")
                .independentMonth(3)
                .independentYear(1)
                .street("서울시 용산구")
                .role(Role.CLIENT)
                .password("password#1234")
                .build();

        member.linkProfile(profile);

        return member;
    }

    public static Member 일반_회원_생성4() {
        Profile profile = Profile.builder()
                .profileImgUrl("https://default-image-url.com/default.png")
                .build();

        Member member = Member.builder()
                .id(123417689L)
                .originName("origin4")
                .email("normal4@test.com")
                .provider(Provider.LOCAL)
                .nickname("일반 회원4")
                .fcmToken("fcmToken12345")
                .independentMonth(3)
                .independentYear(1)
                .street("서울시 서대문구")
                .role(Role.CLIENT)
                .password("password#1234")
                .build();

        member.linkProfile(profile);

        return member;
    }

    public static Member 일반_회원_생성5() {
        Profile profile = Profile.builder()
                .profileImgUrl("https://default-image-url.com/default.png")
                .build();

        Member member = Member.builder()
                .id(12341234L)
                .originName("origin5")
                .email("normal5@test.com")
                .provider(Provider.NAVER)
                .nickname("일반 회원5")
                .fcmToken("fcmToken12345")
                .independentMonth(3)
                .independentYear(1)
                .street("서울시 성동구")
                .role(Role.CLIENT)
                .password("password#1234")
                .build();

        member.linkProfile(profile);

        return member;
    }

    public static List<Member> 회원_5명_생성() {
        return List.of(일반_회원_생성1(), 일반_회원_생성2(), 일반_회원_생성3(), 일반_회원_생성4(), 일반_회원_생성5());
    }

    public static Member 관리자_계정_생성() {
        return Member.builder()
                .originName("adminOrigin")
                .email("admin@test.com")
                .provider(Provider.KAKAO)
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