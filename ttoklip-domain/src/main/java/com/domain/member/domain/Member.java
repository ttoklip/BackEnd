package com.domain.member.domain;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartMember;
import com.domain.common.base.BaseEntity;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentLike;
import com.domain.report.domain.Report;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityLike;
import com.domain.community.domain.CommunityScrap;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipLike;
import com.domain.honeytip.domain.HoneyTipScrap;
import com.domain.interest.domain.Interest;
import com.domain.member.domain.MemberEditor.MemberEditorBuilder;
import com.domain.member.domain.vo.Provider;
import com.domain.member.domain.vo.Role;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterLike;
import com.domain.newsletter.domain.NewsletterScrap;
import com.domain.profile.domain.Profile;
import com.domain.profile.domain.ProfileLike;
import com.domain.question.domain.Question;
import com.domain.term.domain.TermAgreement;
import com.domain.todolist.domain.TodayToDoList;
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

    // 월을 증가시키는 메서드
    public void incrementMonth() {
        this.independentMonth++;
        if (this.independentMonth > 12) {
            this.independentMonth = 1;
            this.independentYear = (this.independentYear + 1) % 100; // 연도가 99를 넘어가면 0으로 초기화
        }
    }
}
