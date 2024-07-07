package com.api.ttoklip.domain.member.domain;

import com.api.ttoklip.domain.common.base.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id") // 'fromMember' field
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id") // 'targetMember' field
    private Member targetMember;

    public static ProfileLike of(final Member fromMember, final Member targetMember) {
        return ProfileLike.builder()
                .fromMember(fromMember)
                .targetMember(targetMember)
                .build();
    }

    public Long getId() {
        return id;
    }

    public Long getFromMemberId() {
        return fromMember.getId();
    }

    public Long getTargetMemberId() {
        return targetMember.getId();
    }
}
