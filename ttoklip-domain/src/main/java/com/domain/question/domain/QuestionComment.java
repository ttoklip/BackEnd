package com.domain.question.domain;

import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentLike;
import com.domain.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Question")
public class QuestionComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "questionComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentLike> commentLikes = new ArrayList<>();


    @Builder
    private QuestionComment(String content, Comment parent, Question question, Member member) {
        super(content, parent, member); // Comment 클래스의 생성자 호출
        this.question = question;
    }

    public static QuestionComment withParentOf(final CommentCreate create, final Comment parent,
                                               final Question question, final Member member) {
        return QuestionComment.builder()
                .content(create.getComment())
                .parent(parent)
                .question(question)
                .member(member)
                .build();
    }

    public static QuestionComment orphanageOf(final CommentCreate create, final Question question,
                                              final Member member) {
        return QuestionComment.builder()
                .content(create.getComment())
                .parent(null)
                .question(question)
                .member(member)
                .build();
    }
}
