package com.api.ttoklip.domain.honeytip.url.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HoneyTipUrl extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honey_tip_id", nullable = false)
    private HoneyTip honeyTip;


    public static HoneyTipUrl of(final HoneyTip honeyTip, final String url) {
        return HoneyTipUrl.builder()
                .url(url)
                .honeyTip(honeyTip)
                .build();
    }

    public void updateHoneyTip(HoneyTip honeyTip) {
        this.honeyTip = honeyTip;
    }
}
