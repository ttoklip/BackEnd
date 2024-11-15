package com.domain.profile.domain;

import com.domain.common.base.BaseTimeEntity;
import com.domain.member.domain.Member;
import jakarta.persistence.Column;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileLike extends BaseTimeEntity {

    @Id
    @Column(name = "id", updatable = false)
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

    public Long getFromMemberId() {
        return fromMember.getId();
    }

    public Long getTargetMemberId() {
        return targetMember.getId();
    }
}
