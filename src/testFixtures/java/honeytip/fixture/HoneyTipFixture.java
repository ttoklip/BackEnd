package honeytip.fixture;

import static member.fixture.MemberFixture.일반_회원_생성1;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipScrap;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import com.api.ttoklip.domain.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import member.fixture.MemberFixture;

@SuppressWarnings("NonAsciiCharacters")
public class HoneyTipFixture {

    // 기본적인 허니팁 생성 (작성자 포함)
    public static HoneyTip 타인_허니팁_생성() {
        Member member = MemberFixture.일반_회원_생성1();
        return HoneyTip.builder()
                .id(1L)
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
        HoneyTip defaultHoneyTip = HoneyTip.builder()
                .title("지정된 멤버 허니팁 제목")
                .content("지정된 멤버 허니팁 내용")
                .category(Category.SAFE_LIVING)
                .member(member)
                .build();


        HoneyTipImage honeyTipImage1 = HoneyTipImage.of(defaultHoneyTip, "https://existing-image1.com");
        HoneyTipImage honeyTipImage2 = HoneyTipImage.of(defaultHoneyTip, "https://existing-image2.com");
        List<HoneyTipImage> images = List.of(honeyTipImage1, honeyTipImage2);

        HoneyTipUrl honeyTipUrl1 = HoneyTipUrl.of(defaultHoneyTip, "https://existing-url1.com");
        HoneyTipUrl honeyTipUrl2 = HoneyTipUrl.of(defaultHoneyTip, "https://existing-url2.com");
        List<HoneyTipUrl> urls = List.of(honeyTipUrl1, honeyTipUrl2);

        return HoneyTip.builder()
                .title(defaultHoneyTip.getTitle())
                .content(defaultHoneyTip.getContent())
                .category(defaultHoneyTip.getCategory())
                .member(defaultHoneyTip.getMember())
                .honeyTipImages(images)
                .honeyTipUrls(urls)
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

    // 카테고리별로 10개씩 허니팁을 생성하는 메서드
    private static List<HoneyTip> 카테고리별_허니팁_리스트_생성(Category category, Member member) {
        List<HoneyTip> honeyTips = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            honeyTips.add(HoneyTip.builder()
                    .title(category + " 허니팁 제목 " + i)
                    .content(category + " 허니팁 내용 " + i)
                    .category(category)
                    .member(member)
                    .build());
        }
        return honeyTips;
    }

    // 카테고리별로 10개씩 허니팁을 생성하는 메서드
    private static List<HoneyTip> 카테고리별_허니팁_N개사이즈_리스트_생성(Category category, Member member, int n) {
        List<HoneyTip> honeyTips = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            honeyTips.add(HoneyTip.builder()
                    .title(category + " 허니팁 제목 " + i)
                    .content(category + " 허니팁 내용 " + i)
                    .category(category)
                    .member(member)
                    .build());
        }
        System.out.println("honeyTips = " + honeyTips);
        return honeyTips;
    }

    // 각 카테고리별 허니팁 리스트 생성
    public static List<HoneyTip> 허니팁_집안일_크기가_10인_리스트_생성() {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_리스트_생성(Category.HOUSEWORK, member);
    }

    public static List<HoneyTip> 허니팁_레시피_크기가_10인_리스트_생성() {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_리스트_생성(Category.RECIPE, member);
    }

    public static List<HoneyTip> 허니팁_안전생활_크기가_10인_리스트_생성() {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_리스트_생성(Category.SAFE_LIVING, member);
    }

    public static List<HoneyTip> 허니팁_복지정책_크기가_10인_리스트_생성() {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_리스트_생성(Category.WELFARE_POLICY, member);
    }

    public static List<HoneyTip> 허니팁_복지정책_크기가_N인_리스트_생성(final int n) {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_N개사이즈_리스트_생성(Category.WELFARE_POLICY, member, n);
    }

    public static List<HoneyTip> 허니팁_안전생활_크기가_N인_리스트_생성(final int n) {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_N개사이즈_리스트_생성(Category.SAFE_LIVING, member, n);
    }

    public static List<HoneyTip> 허니팁_레시피_크기가_N인_리스트_생성(final int n) {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_N개사이즈_리스트_생성(Category.RECIPE, member, n);
    }

    public static List<HoneyTip> 허니팁_집안일_크기가_N인_리스트_생성(final int n) {
        Member member = 일반_회원_생성1();
        return 카테고리별_허니팁_N개사이즈_리스트_생성(Category.HOUSEWORK, member, n);
    }

    public static List<HoneyTip> 랜덤_카테고리와_랜덤_개수의_댓글_좋아요_스크랩_포함한_허니팁_생성(List<Member> members) {
        Random random = new Random();
        List<HoneyTip> honeyTips = new ArrayList<>();
        Category[] categories = Category.values();

        for (int i = 0; i < 10; i++) {
            Category category = categories[i % categories.length];

            Member randomMember = members.get(random.nextInt(members.size()));

            HoneyTip honeyTip = HoneyTip.builder()
                    .title("허니팁 제목 " + i)
                    .content("허니팁 내용 " + i)
                    .category(category)
                    .member(randomMember)
                    .build();

            // 댓글, 좋아요, 스크랩 개수를 무작위로 생성
            List<HoneyTipComment> comments = IntStream.range(0, random.nextInt(10))
                    .mapToObj(j -> HoneyTipComment.builder()
                            .member(members.get(random.nextInt(members.size())))
                            .content("댓글 내용 " + j)
                            .honeyTip(honeyTip)
                            .build())
                    .toList();

            List<HoneyTipLike> likes = IntStream.range(0, random.nextInt(10))
                    .mapToObj(j -> HoneyTipLike.builder()
                            .member(members.get(random.nextInt(members.size())))
                            .honeyTip(honeyTip)
                            .build())
                    .toList();

            List<HoneyTipScrap> scraps = IntStream.range(0, random.nextInt(10))
                    .mapToObj(j -> HoneyTipScrap.builder()
                            .member(members.get(random.nextInt(members.size())))
                            .honeyTip(honeyTip)
                            .build())
                    .toList();

            HoneyTip result = HoneyTip.builder()
                    .title(honeyTip.getTitle())
                    .content(honeyTip.getContent())
                    .category(honeyTip.getCategory())
                    .member(honeyTip.getMember())
                    .honeyTipComments(comments)
                    .honeyTipLikes(likes)
                    .honeyTipScraps(scraps)
                    .build();

            honeyTips.add(result);
        }
        return honeyTips;
    }

}
