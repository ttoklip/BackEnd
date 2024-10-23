package com.api.ttoklip.domain.mypage.domain;


import com.api.ttoklip.domain.question.domain.Question;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.ttoklip.domain.question.domain.QQuestion.question;
import static com.api.ttoklip.domain.question.domain.QQuestionComment.questionComment;

@Repository
@RequiredArgsConstructor
public class MyQuestionRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Question> getContain(final Long userId, final Pageable pageable) {
        List<Question> content = getSearchPageId(userId, pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Question> getSearchPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .where(question.member.id.eq(userId).and(question.deleted.eq(false)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(question.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(question)
                .fetchOne();
    }
}
