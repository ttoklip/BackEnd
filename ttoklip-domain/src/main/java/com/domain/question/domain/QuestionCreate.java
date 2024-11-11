package com.domain.question.domain;

import com.domain.common.vo.Category;
import com.domain.common.vo.PostRequest;
import com.domain.member.domain.Member;

public record QuestionCreate(String title, String content, Category category, Member member) implements PostRequest {
    public static QuestionCreate of(String title, String content, Category category, Member member) {
        return new QuestionCreate(title, content, category, member);
    }
}
