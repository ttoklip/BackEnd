package com.api.ttoklip.domain.email.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "email", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_status", nullable = false)
    private boolean emailStatus;

    @Builder
    private Email(String email, boolean emailStatus) {
        this.email = email;
        this.emailStatus = false;
    }

    public static Email from(String email){
        return new Email(email, false);
    }
}

