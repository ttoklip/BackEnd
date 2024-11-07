package com.api.ttoklip.domain.honeytip.domain;

import com.api.ttoklip.domain.common.base.BaseTimeEntity;
import com.api.ttoklip.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HoneyTipScrap extends BaseTimeEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honey_tip_id")
    private HoneyTip honeyTip;

    public static HoneyTipScrap of(final HoneyTip honeyTip, final Member member) {
        return HoneyTipScrap.builder()
                .member(member)
                .honeyTip(honeyTip)
                .build();
    }
}
