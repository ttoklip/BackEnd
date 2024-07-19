package com.api.ttoklip.domain.mypage.term.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermAgreement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDateTime agreedDate;

    private boolean term1Agreement;
    private boolean term2Agreement;
    private boolean term3Agreement;
    private boolean term4Agreement;

    public static TermAgreement of(Member member,
                                   boolean term1Agreement,
                                   boolean term2Agreement,
                                   boolean term3Agreement,
                                   boolean term4Agreement) {
        return TermAgreement.builder()
                .member(member)
                .agreedDate(LocalDateTime.now())
                .term1Agreement(term1Agreement)
                .term2Agreement(term2Agreement)
                .term3Agreement(term3Agreement)
                .term4Agreement(term4Agreement)
                .build();
    }
}
