package com.api.ttoklip.domain.member.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.honeytip.like.domain.HoneyTipLike;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.question.post.domain.Question;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTip> honeyTips = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipLike> honeyTipLikes = new ArrayList<>();

}