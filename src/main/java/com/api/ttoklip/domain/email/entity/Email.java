package com.api.ttoklip.domain.email.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_status", nullable = false)
    private boolean emailStatus;

    @Builder
    public Email(String email) {
        this.email = email;
        this.emailStatus = false;
    }
}

