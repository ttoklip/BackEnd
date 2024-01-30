package com.api.ttoklip.domain.common.base;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity{
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    //    private boolean status;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    public void updateStatus(Status status) {
        this.status = status;
    }

}
