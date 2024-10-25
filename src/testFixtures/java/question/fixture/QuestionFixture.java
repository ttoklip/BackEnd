package question.fixture;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.Question;

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


}
