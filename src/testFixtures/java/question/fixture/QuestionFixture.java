package question.fixture;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.Question;
import member.fixture.MemberFixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("NonAsciiCharacters")
public class QuestionFixture {

    // 주어진 작성자 멤버를 이용해 질문 생성
    public static Question 본인_질문_생성(final Member member) {
        return Question.builder()
                .title("지정된 멤버 질문 제목")
                .content("지정된 멤버 질문 내용")
                .category(Category.HOUSEWORK)
                .member(member)
                .build();
    }

    // 기본적인 질문 생성 (작성자 포함)
    public static Question 타인_질문_생성() {
        Member member = MemberFixture.일반_회원_생성1();
        return Question.builder()
                .id(1L)
                .title("기본 질문 제목")
                .content("기본 질문 내용")
                .category(Category.HOUSEWORK)
                .member(member)
                .build();
    }

    // 카테고리별 질문 리스트 생성 메서드들 수정
    public static List<Question> 질문_집안일_크기가_N인_리스트_생성(int n) {
        return 질문_크기가_N인_리스트_생성(n, Category.HOUSEWORK);
    }

    public static List<Question> 질문_레시피_크기가_N인_리스트_생성(int n) {
        return 질문_크기가_N인_리스트_생성(n, Category.RECIPE);
    }

    public static List<Question> 질문_안전생활_크기가_N인_리스트_생성(int n) {
        return 질문_크기가_N인_리스트_생성(n, Category.SAFE_LIVING);
    }

    public static List<Question> 질문_복지정책_크기가_N인_리스트_생성(int n) {
        return 질문_크기가_N인_리스트_생성(n, Category.WELFARE_POLICY);
    }

    // 랜덤한 크기의 질문 리스트를 생성하는 메서드 추가
    public static List<Question> 질문_랜덤_크기_리스트_생성(int n) {
        Category[] categories = Category.values();
        List<Question> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Category randomCategory = categories[new Random().nextInt(categories.length)];
            list.add(Question.builder()
                    .id((long) i + 1)
                    .title("랜덤 질문 제목 " + i)
                    .content("랜덤 질문 내용 " + i)
                    .category(randomCategory)
                    .member(MemberFixture.일반_회원_생성1())
                    .build());
        }
        return list;
    }

    // 공통 메서드: 특정 카테고리와 크기로 질문 리스트 생성
    private static List<Question> 질문_크기가_N인_리스트_생성(int n, Category category) {
        List<Question> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(Question.builder()
                    .id((long) i + 1)
                    .title(category.name() + " 질문 제목 " + i)
                    .content(category.name() + " 질문 내용 " + i)
                    .category(category)
                    .member(MemberFixture.일반_회원_생성1())
                    .build());
        }
        return list;
    }
}
