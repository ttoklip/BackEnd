package com.api.ttoklip.domain.town.community.scrap.entity;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import jakarta.persistence.*;
import lombok.*;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    public static CommunityScrap from(final Community community) {
        return CommunityScrap.builder()
                .member(getCurrentMember())
                .community(community)
                .build();
    }
}
