package com.api.ttoklip.domain.honeytip.post.image.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneyTipCreateReq;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HoneyTipImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honey_tip_id", nullable = false)
    private HoneyTip honeyTip;

    public void updateHoneyTip(HoneyTip honeyTip) {
        this.honeyTip = honeyTip;
    }

    public static HoneyTipImage of(final HoneyTip honeyTip, final String url) {
        return HoneyTipImage.builder()
                .honeyTip(honeyTip)
                .url(url)
                .build();
    }
}
