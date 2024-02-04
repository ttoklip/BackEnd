package com.api.ttoklip.domain.mypage.noti.post.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeCreateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public static Notice of (final NoticeCreateRequest request){
        return Notice.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();

    }
}
