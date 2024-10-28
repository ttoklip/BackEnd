package comment.fixture;

import static member.fixture.MemberFixture.일반_회원_생성1;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.domain.QuestionComment;

@SuppressWarnings("NonAsciiCharacters")
public class CommentFixture {

    // 부모 댓글이 있는 경우 (대댓글 생성 요청)
    public static CommentCreateRequest 대댓글_생성_요청(Comment parentComment) {
        String comment = "부모 댓글에 대한 대댓글 생성 내용";
        return new CommentCreateRequest(comment, parentComment.getId());
    }

    // 최상위 댓글 생성 요청 (부모 댓글 없는 최상위 댓글)
    public static CommentCreateRequest 최상위_댓글_생성_요청() {
        String comment = "최상위 댓글 생성 내용";
        return new CommentCreateRequest(comment, null); // 부모 댓글 ID 없음
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
    private static CommentCreateRequest 댓글_생성_요청() {
        return new CommentCreateRequest("허니팁 댓글 내용", null);
    }

    // 질문 최상위 댓글 생성
    public static QuestionComment 질문_최상위_댓글_생성(Question question) {
        Member member = 일반_회원_생성1();
        return QuestionComment.orphanageOf(최상위_댓글_생성_요청(), question, member);
    }


}
