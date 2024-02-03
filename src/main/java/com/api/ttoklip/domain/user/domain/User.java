package com.api.ttoklip.domain.user.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "provider_id", unique = true, updatable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public User(String email, String name, String providerId, Role role) {
        this.email = email;
        this.name = name;
        this.providerId = providerId;
        this.role = role;
    }

}
