package com.domain.report.domain;

import com.domain.cart.domain.Cart;
import com.domain.common.base.BaseEntity;
import com.domain.comment.domain.Comment;
import com.domain.community.domain.Community;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.domain.Member;
import com.domain.newsletter.domain.Newsletter;
import com.domain.question.domain.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Report extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id")
    private Newsletter newsletter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honey_tip_id")
    private HoneyTip honeyTip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static Report memberOf(final ReportCreate request, final Member reportedMember, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .reporter(reporter)
                .reportedMember(reportedMember)
                .build();
    }

    public static Report questionOf(final ReportCreate request, final Question question, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .question(question)
                .reporter(reporter)
                .reportedMember(question.getMember())
                .build();
    }

    public static Report newsletterOf(final ReportCreate request, final Newsletter newsletter, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .newsletter(newsletter)
                .reporter(reporter)
                .reportedMember(newsletter.getMember())
                .build();
    }

    public static Report communityOf(final ReportCreate request, final Community community, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .community(community)
                .reporter(reporter)
                .reportedMember(community.getMember())
                .build();
    }

    public static Report cartOf(final ReportCreate request, final Cart cart, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .cart(cart)
                .reporter(reporter)
                .reportedMember(cart.getMember())
                .build();
    }

    public static Report honeyTipOf(final ReportCreate request, final HoneyTip honeyTip, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .honeyTip(honeyTip)
                .reporter(reporter)
                .reportedMember(honeyTip.getMember())
                .build();
    }

    public static Report commentOf(final ReportCreate request, final Comment comment, final Member reporter) {
        return Report.builder()
                .content(request.content())
                .reportType(request.reportType())
                .comment(comment)
                .reporter(reporter)
                .reportedMember(comment.getMember())
                .build();
    }

}
