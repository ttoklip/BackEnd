package com.api.ttoklip.domain.common.comment;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.base.BaseTimeEntity;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLike extends BaseTimeEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private QuestionComment questionComment;

//    @Schema(description = "현재 사용자의 해당 댓글 좋아요 여부")
//    private boolean likedByCurrentUser;

    public static CommentLike from(final Member member, final QuestionComment questionComment) {
        return CommentLike.builder()
                .member(member)
                .questionComment(questionComment)
                .build();
    }

}
