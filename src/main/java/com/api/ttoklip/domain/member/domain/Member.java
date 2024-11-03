package com.api.ttoklip.domain.member.domain;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipScrap;
import com.api.ttoklip.domain.member.domain.vo.Provider;
import com.api.ttoklip.domain.member.domain.vo.Role;
import com.api.ttoklip.domain.member.editor.MemberEditor;
import com.api.ttoklip.domain.member.editor.MemberEditor.MemberEditorBuilder;
import com.api.ttoklip.domain.newsletter.domain.NewsletterLike;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.NewsletterScrap;
import com.api.ttoklip.domain.notification.entity.Notification;
import com.api.ttoklip.domain.privacy.domain.Interest;
import com.api.ttoklip.domain.privacy.domain.Profile;
import com.api.ttoklip.domain.profile.domain.ProfileLike;
import com.api.ttoklip.domain.question.like.entity.CommentLike;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.term.domain.TermAgreement;
import com.api.ttoklip.domain.todolist.domain.TodayToDoList;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.entity.CartMember;
import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.scrap.entity.CommunityScrap;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class Member extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originName;

    @Email
    private String email;
    private String password;
    private Provider provider;
    private String nickname;
    private String street;
    private int independentYear;
    private int independentMonth;

    @Column(name = "fcm_token", columnDefinition = "LONGTEXT")
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TermAgreement> termAgreements = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    // 신고한 리포트 리스트
    @Builder.Default
    @OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Report> reportedReports = new ArrayList<>();

    // 신고당한 리포트 리스트
    @Builder.Default
    @OneToMany(mappedBy = "reportedMember", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Report> receivedReports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTip> honeyTips = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipLike> honeyTipLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipScrap> honeyTipScraps = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Community> communities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLike> communityLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityScrap> communityScraps = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Newsletter> newsletters = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterLike> newsletterLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsletterScrap> newsletterScraps = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartMember> cartMembers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "fromMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileLike> profileLikesFrom = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileLike> profileLikesTo = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodayToDoList> todayToDoLists = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    public MemberEditorBuilder toEditor() {
        return MemberEditor.builder()
                .independentYear(independentYear)
                .independentMonth(independentMonth)
                .nickname(nickname)
                .street(street);
    }

    public void insertPrivacy(final MemberEditor memberEditor) {
        independentYear = memberEditor.getIndependentYear();
        independentMonth = memberEditor.getIndependentMonth();
        nickname = memberEditor.getNickname();
        street = memberEditor.getStreet();
    }

    public void updateFcmToken(final String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void linkProfile(final Profile profile) {
        this.profile = profile;
    }
}
