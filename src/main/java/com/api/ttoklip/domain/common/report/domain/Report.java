package com.api.ttoklip.domain.common.report.domain;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
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

    public static Report memberOf(final ReportCreateRequest request, final Member reportedMember) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .reporter(getCurrentMember())
                .reportedMember(reportedMember)
                .build();
    }

    public static Report questionOf(final ReportCreateRequest request, final Question question) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .question(question)
                .reporter(getCurrentMember())
                .reportedMember(question.getMember())
                .build();
    }

    public static Report newsletterOf(final ReportCreateRequest request, final Newsletter newsletter) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .newsletter(newsletter)
                .reporter(getCurrentMember())
                .reportedMember(newsletter.getMember())
                .build();
    }

    public static Report communityOf(final ReportCreateRequest request, final Community community) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .community(community)
                .reporter(getCurrentMember())
                .reportedMember(community.getMember())
                .build();
    }

    public static Report cartOf(final ReportCreateRequest request, final Cart cart) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .cart(cart)
                .reporter(getCurrentMember())
                .reportedMember(cart.getMember())
                .build();
    }

    public static Report honeyTipOf(final ReportCreateRequest request, final HoneyTip honeyTip) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .honeyTip(honeyTip)
                .reporter(getCurrentMember())
                .reportedMember(honeyTip.getMember())
                .build();
    }

    public static Report commentOf(final ReportCreateRequest request, final Comment comment) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .comment(comment)
                .reporter(getCurrentMember())
                .reportedMember(comment.getMember())
                .build();
    }

}
