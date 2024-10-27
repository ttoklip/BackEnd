package question.fixture;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.Question;
import member.fixture.MemberFixture;

import java.util.ArrayList;
import java.util.List;

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

    // 질문 리스트 생성 메서드들 추가
    public static List<Question> 질문_집안일_리스트_생성() {
        List<Question> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Question.builder()
                    .id((long) i)
                    .title("집안일 질문 제목 " + i)
                    .content("집안일 질문 내용 " + i)
                    .category(Category.HOUSEWORK)
                    .member(MemberFixture.일반_회원_생성1())
                    .build());
        }
        return list;
    }

    public static List<Question> 질문_레시피_리스트_생성() {
        List<Question> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Question.builder()
                    .id((long) i)
                    .title("레시피 질문 제목 " + i)
                    .content("레시피 질문 내용 " + i)
                    .category(Category.RECIPE)
                    .member(MemberFixture.일반_회원_생성1())
                    .build());
        }
        return list;
    }

    public static List<Question> 질문_안전생활_리스트_생성() {
        List<Question> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Question.builder()
                    .id((long) i)
                    .title("안전생활 질문 제목 " + i)
                    .content("안전생활 질문 내용 " + i)
                    .category(Category.SAFE_LIVING)
                    .member(MemberFixture.일반_회원_생성1())
                    .build());
        }
        return list;
    }

    public static List<Question> 질문_복지정책_리스트_생성() {
        List<Question> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Question.builder()
                    .id((long) i)
                    .title("복지정책 질문 제목 " + i)
                    .content("복지정책 질문 내용 " + i)
                    .category(Category.WELFARE_POLICY)
                    .member(MemberFixture.일반_회원_생성1())
                    .build());
        }
        return list;
    }
}
