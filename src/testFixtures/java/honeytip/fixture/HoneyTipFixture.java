package honeytip.fixture;

import static member.fixture.MemberFixture.일반_회원_생성;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import com.api.ttoklip.domain.member.domain.Member;
import java.util.List;
import member.fixture.MemberFixture;

@SuppressWarnings("NonAsciiCharacters")
public class HoneyTipFixture {

    // 기본적인 허니팁 생성 (작성자 포함)
    public static HoneyTip 타인_허니팁_생성() {
        Member member = MemberFixture.일반_회원_생성();
        return HoneyTip.builder()
                .title("기본 허니팁 제목")
                .content("기본 허니팁 내용")
                .category(Category.HOUSEWORK)
                .member(member)
                .build();
    }

    // 주어진 작성자 멤버를 이용해 허니팁 생성
    public static HoneyTip 본인_허니팁_생성(final Member member) {
        return HoneyTip.builder()
                .title("지정된 멤버 허니팁 제목")
                .content("지정된 멤버 허니팁 내용")
                .category(Category.SAFE_LIVING)
                .member(member)
                .build();
    }

    public static HoneyTip 본인_허니팁_사진_URL_포함하여_생성(final Member member) {
        HoneyTip honeyTip = HoneyTip.builder()
                .title("지정된 멤버 허니팁 제목")
                .content("지정된 멤버 허니팁 내용")
                .category(Category.SAFE_LIVING)
                .member(member)
                .build();

        honeyTip.addImage(HoneyTipImage.of(honeyTip, "https://existing-image1.com"));
        honeyTip.addImage(HoneyTipImage.of(honeyTip, "https://existing-image2.com"));

        honeyTip.addUrl(HoneyTipUrl.of(honeyTip, "https://existing-url1.com"));
        honeyTip.addUrl(HoneyTipUrl.of(honeyTip, "https://existing-url2.com"));

        return honeyTip;
    }

    public static HoneyTipCreateRequest URL_X_사진_X_단순_꿀팁_게시글_요청_픽스처() {
        return HoneyTipCreateRequest.builder()
                .title("Netflix 요금 저렴하게 가입하기")
                .content("유용한 팁을 공유합니다.")
                .category(Category.HOUSEWORK.name())
                .url(null)
                .images(null)
                .build();
    }

    public static List<String> URLS_픽스쳐() {
        return List.of("www.abc.net1", "www.abc.net2", "www.abc.net3");
    }

    // 추가 픽스처: URL이 포함된 꿀팁 게시글 요청
    public static HoneyTipCreateRequest URL_있음_게시글_요청_픽스처() {
        List<String> urls = URLS_픽스쳐();
        return HoneyTipCreateRequest.builder()
                .title("URL이 있는 허니팁")
                .content("유용한 정보를 공유합니다.")
                .category(Category.SAFE_LIVING.name())
                .url(urls)
                .images(null)
                .build();
    }

}
