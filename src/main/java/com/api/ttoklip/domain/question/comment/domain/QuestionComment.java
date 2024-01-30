package com.api.ttoklip.domain.question.comment.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.question.post.domain.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Question")
public class QuestionComment extends Comment {

    @Builder
    private QuestionComment(String content, Comment parent, Question question) {
        super(content, question, parent); // Comment 클래스의 생성자 호출
    }

    public static QuestionComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                               final Question question) {
        return QuestionComment.builder()
                .content(request.getComment())
                .parent(parent)
                .question(question)
                .build();
    }

    public static QuestionComment orphanageOf(final CommentCreateRequest request, final Question question) {
        return QuestionComment.builder()
                .content(request.getComment())
                .parent(null)
                .question(question)
                .build();
    }
}
