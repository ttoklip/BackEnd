package com.api.ttoklip.domain.user.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Entity
@Table(name = "User")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;


    @Column(name = "name")
    private String name;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "provider_id", unique = true, updatable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    private Integer livingAloneYears;

    private Integer livingAloneMonths;

    private Boolean isRecvActive; // 알람 활성화 여부

    private Boolean isLocationConsent; // 위치 서비스 이용 동의 여부

    private List<Category> categories; // 고민거리(카테고리 선택)


    @Builder
    public User(Long id, String email, String name, String nickname, Provider provider, String providerId, Role role, Integer livingAloneYears, Integer livingAloneMonths, Boolean isRecvActive, Boolean isLocationConsent, List<Category> categories) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.livingAloneYears = livingAloneYears;
        this.livingAloneMonths = livingAloneMonths;
        this.isRecvActive = isRecvActive;
        this.isLocationConsent = isLocationConsent;
        this.categories = categories;
    }
}
