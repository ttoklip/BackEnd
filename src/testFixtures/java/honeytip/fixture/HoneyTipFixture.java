package honeytip.fixture;

import static member.fixture.MemberFixture.일반_회원_생성;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class HoneyTipFixture {

    // 기존 픽스처
    public static HoneyTip 임의_작성자_허니팁_생성() {
        Member member = 일반_회원_생성();
        return 허니팁_생성(member, "허니팁 제목", "허니팁 내용", Category.HOUSEWORK);
    }

    public static HoneyTip 허니팁_생성(final Member member, String title, String content, Category category) {
        return HoneyTip.builder()
                .title(title)
                .content(content)
                .category(category)
                .member(member)
                .build();
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

    public static HoneyTipCreateRequest 내용에_욕설이_포함된_꿀팁_게시글_요청_픽스처() {
        return HoneyTipCreateRequest.builder()
                .title("Netflix 요금 저렴하게 가입하기")
                .content("fuck")
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
