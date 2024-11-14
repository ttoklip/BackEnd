//package com.domain.bulletin.infrastructure;
//
//
//import com.common.exception.ApiException;
//import com.common.exception.ErrorType;
//import com.domain.bulletin.domain.Notice;
//import com.domain.bulletin.domain.NoticeResponses;
//import com.domain.bulletin.domain.QNotice;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class NoticeQueryRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    private final QNotice notice = QNotice.notice;
//
//    public Notice findByIdActivated(final Long noticeId) {
//        Notice findNotice = jpaQueryFactory
//                .selectFrom(notice)
//                .where(
//                        matchId(noticeId), getNoticeActivate()
//                )
//                .fetchOne();
//        return Optional.ofNullable(findNotice)
//                .orElseThrow(() -> new ApiException(ErrorType.NOTICE_NOT_FOUND));
//    }
//
//    private BooleanExpression matchId(final Long noticeId) {
//        return notice.id.eq(noticeId);
//    }
//
//    private BooleanExpression getNoticeActivate() {
//        return notice.deleted.isFalse();
//    }
//
//    public NoticeResponses getContain(final int pageNumber, final int pageSize) {
//        List<Notice> notices = getPageContent(pageNumber, pageSize);
//        boolean isLastPage = isLastPage(pageNumber, pageSize);
//        return NoticeResponses.from(notices, isLastPage);
//    }
//
//    private List<Notice> getPageContent(final int pageNumber, final int pageSize) {
//        return jpaQueryFactory
//                .selectFrom(notice)
//                .where(notice.deleted.eq(false))
//                .limit(pageSize)
//                .offset((long) pageNumber * pageSize)
//                .orderBy(notice.id.desc())
//                .fetch();
//    }
//
//    public boolean isLastPage(final int pageNumber, final int pageSize) {
//        long nextOffset = (long) (pageNumber + 1) * pageSize;
//        return jpaQueryFactory
//                .selectOne() // 다음 페이지 데이터 존재 여부만 확인
//                .from(notice)
//                .where(notice.deleted.eq(false))
//                .offset(nextOffset)
//                .limit(1)
//                .fetchOne() == null; // 다음 페이지가 없으면 true 반환
//    }
//
//
//}
