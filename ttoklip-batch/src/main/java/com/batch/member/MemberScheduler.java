package com.batch.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberScheduler {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Transactional
    // 매일 자정 3분에 실행 (서울 기준)
    @Scheduled(cron = "0 3 0 * * *", zone = "Asia/Seoul")
    public void updateMemberIndependenceDate() {
        // 모든 회원들에 대해 월만 1 증가시키기
        queryFactory
                .update(member)
                .set(member.independentMonth, member.independentMonth.add(1))
                .execute();

        // 월이 12인 회원들에 대해 월을 0으로 초기화하고 연도를 1 증가시키기
        queryFactory
                .update(member)
                .set(member.independentMonth, 0)
                .set(member.independentYear, member.independentYear.add(1).mod(100)) // 연도가 99를 넘어가면 0으로 초기화
                .where(member.independentMonth.eq(12)) // 12월인 경우에만 적용
                .execute();

        entityManager.clear();
    }

}
