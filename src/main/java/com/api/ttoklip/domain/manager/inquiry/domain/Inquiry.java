package com.api.ttoklip.domain.manager.inquiry.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.manager.inquiry.dto.request.InquiryCreateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Inquiry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name = "content")
    private String content;

    public static Inquiry of (final InquiryCreateRequest request){
        return Inquiry.builder()
                .content(request.getContent())
                .build();
    }
    public void deactivate() {
        super.deactivate();// inquiry 엔티티 비활성화
    }

}
