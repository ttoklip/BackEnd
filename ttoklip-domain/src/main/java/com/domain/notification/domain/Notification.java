package com.domain.notification.domain;

import com.domain.common.base.BaseEntity;
import com.domain.member.domain.Member;
import com.domain.notification.domain.vo.NotiCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(value = EnumType.STRING)
    private NotiCategory notiCategory;

    private boolean status;

    private String targetType;

    private Long targetIndex;


    @Builder
    private Notification(final Member member, final String title, final String text,
                         final NotiCategory notiCategory, String targetType, Long targetIndex, boolean status) {
        this.member = member;
        this.title = title;
        this.text = text;
        this.notiCategory = notiCategory;
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.status = status;
    }

    public static Notification of(Member member, String title, String text,
                                  NotiCategory notiCategory, String targetType,
                                  Long targetIndex, boolean status) {
        return Notification.builder()
                .member(member)
                .title(title)
                .text(text)
                .notiCategory(notiCategory)
                .targetType(targetType)
                .targetIndex(targetIndex)
                .status(status)
                .build();
    }
}
