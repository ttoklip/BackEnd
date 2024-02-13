package com.api.ttoklip.domain.manager.inquiry.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.manager.inquiry.dto.request.FaqCreateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Faq extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name = "content")
    private String content;

    public static Faq of (final FaqCreateRequest request){
        return Faq.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
    public void deactivate() {
        super.deactivate();// inquiry 엔티티 비활성화
    }
}
