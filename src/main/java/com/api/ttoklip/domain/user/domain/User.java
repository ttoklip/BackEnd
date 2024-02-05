package com.api.ttoklip.domain.user.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", unique = true, updatable = false)
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
    @Column(name = "role")
    private Role role;

    private Integer livingAloneYears;

    private Integer livingAloneMonths;

    private Boolean isRecvActive; // 알람 활성화 여부

    private Boolean isLocationConsent; // 위치 서비스 이용 동의 여부

    private List<Category> categories; // 고민거리(카테고리 선택)


}
