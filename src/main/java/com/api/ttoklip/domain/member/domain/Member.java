package com.api.ttoklip.domain.member.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    private String naverEmail;

    private Long kakaoId;

    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
    private Profile profile;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();












    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLike> communityLikes = new ArrayList<>();

}