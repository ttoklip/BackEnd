package com.api.ttoklip.domain.question.post.repository;

import static com.api.ttoklip.domain.member.domain.QMember.*;
import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;
import static com.api.ttoklip.domain.question.post.domain.QQuestion.question;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionDefaultRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Question> getHouseWork() {
        return jpaQueryFactory
                .select(question)
                .from(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member)
                .fetchJoin()
                .where(question.category.eq(Category.HOUSEWORK))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<Question> getRecipe() {
        return jpaQueryFactory
                .select(question)
                .from(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member)
                .fetchJoin()
                .where(question.category.eq(Category.RECIPE))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<Question> getSafeLiving() {
        return jpaQueryFactory
                .select(question)
                .from(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member)
                .fetchJoin()
                .where(question.category.eq(Category.SAFE_LIVING))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<Question> getWelfarePolicy() {
        return jpaQueryFactory
                .select(question)
                .from(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member)
                .fetchJoin()
                .where(question.category.eq(Category.WELFARE_POLICY))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }
}
