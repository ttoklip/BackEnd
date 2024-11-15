package comment.fixture;

import static member.fixture.MemberFixture.일반_회원_생성1;

import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.member.domain.Member;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionComment;

@SuppressWarnings("NonAsciiCharacters")
public class CommentFixture {

    // 부모 댓글이 있는 경우 (대댓글 생성 요청)
    public static CommentCreate 대댓글_생성_요청(Comment parentComment) {
        String comment = "부모 댓글에 대한 대댓글 생성 내용";
        return CommentCreate.of(comment, parentComment.getId());
    }

    // 최상위 댓글 생성 요청 (부모 댓글 없는 최상위 댓글)
    public static CommentCreate 최상위_댓글_생성_요청() {
        String comment = "최상위 댓글 생성 내용";
        return CommentCreate.of(comment, null);
    }

    // 부모 댓글이 있는 대댓글 생성
    public static HoneyTipComment 꿀팁_대댓글_생성(HoneyTipComment parentComment, final HoneyTip honeyTip) {
        Member member = 일반_회원_생성1();
        return HoneyTipComment.withParentOf(대댓글_생성_요청(parentComment), parentComment, honeyTip, member);
    }

    // 꿀팁 최상위 댓글 생성
    public static HoneyTipComment 꿀팁_최상위_댓글_생성(HoneyTip honeyTip) {
        Member member = 일반_회원_생성1();
        return HoneyTipComment.orphanageOf(최상위_댓글_생성_요청(), honeyTip, member);
    }

    // 댓글 생성 요청을 일반적으로 사용할 경우
    private static CommentCreate 댓글_생성_요청() {
        String comment = "허니팁 댓글 내용";
        return CommentCreate.of(comment, null);
    }

    // 질문 최상위 댓글 생성
    public static QuestionComment 질문_최상위_댓글_생성(Question question) {
        Member member = 일반_회원_생성1();
        return QuestionComment.orphanageOf(최상위_댓글_생성_요청(), question, member);
    }

}