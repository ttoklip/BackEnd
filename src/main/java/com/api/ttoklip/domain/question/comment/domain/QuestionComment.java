package com.api.ttoklip.domain.question.comment.domain;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.like.entity.CommentLike;
import com.api.ttoklip.domain.question.post.domain.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Question")
public class QuestionComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "questionComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes  = new ArrayList<>();



    @Builder
    private QuestionComment(String content, Comment parent, Question question, Member member) {
        super(content, parent, member); // Comment 클래스의 생성자 호출
        this.question = question;
    }

    public static QuestionComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                               final Question question) {
        return QuestionComment.builder()
                .content(request.getComment())
                .parent(parent)
                .question(question)
                .member(getCurrentMember())
                .build();
    }

    public static QuestionComment orphanageOf(final CommentCreateRequest request, final Question question) {
        return QuestionComment.builder()
                .content(request.getComment())
                .parent(null)
                .question(question)
                .member(getCurrentMember())
                .build();
    }
}
