package com.api.ttoklip.domain.question.comment.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.question.post.domain.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Question")
public class QuestionComment extends Comment {

    public static QuestionComment of(final CommentCreateRequest request, final Comment parent,
                                     final Question question) {
        return QuestionComment.builder()
                .content(request.getComment())
                .parent(parent)
                .question(question)
                .build();
    }
}
