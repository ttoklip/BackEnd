package com.api.ttoklip.domain.honeytip.Scrap.domain;

import com.api.ttoklip.domain.common.base.BaseTimeEntity;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HoneyTipScrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honey_tip_id")
    private HoneyTip honeyTip;

    public static HoneyTipScrap from(final HoneyTip honeyTip) {
        return HoneyTipScrap.builder()
                .member(getCurrentMember())
                .honeyTip(honeyTip)
                .build();
    }
}
