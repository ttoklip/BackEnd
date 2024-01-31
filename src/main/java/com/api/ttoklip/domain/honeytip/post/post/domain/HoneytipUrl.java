package com.api.ttoklip.domain.honeytip.post.post.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Honeytip_Url")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class HoneytipUrl extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honeytip_id", nullable = false)
    private Honeytip honeytip;

    public void updateHoneytip(Honeytip honeytip) {
        this.honeytip = honeytip;
    }

    public HoneytipUrl(String url, Honeytip honeytip) {
        this.url = url;
        this.honeytip = honeytip;
    }
}
