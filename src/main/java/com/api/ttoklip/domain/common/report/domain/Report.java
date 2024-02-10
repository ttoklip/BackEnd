package com.api.ttoklip.domain.common.report.domain;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
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

    @Column(length = 300)
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    public static Report questionOf(final ReportCreateRequest request, final Question question) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .question(question)
                .member(getCurrentMember())
                .build();
    }

    public static Report newsletterOf(final ReportCreateRequest request, final Newsletter newsletter) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .newsletter(newsletter)
                .member(getCurrentMember())
                .build();
    }

    public static Report communityOf(final ReportCreateRequest request, final Community community) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .community(community)
                .member(getCurrentMember())
                .build();
    }

    public static Report cartOf(final ReportCreateRequest request, final Cart cart) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .cart(cart)
                .member(getCurrentMember())
                .build();
    }

    public static Report honeyTipOf(final ReportCreateRequest request, final HoneyTip honeyTip) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .honeyTip(honeyTip)
                .member(getCurrentMember())
                .build();
    }

    public static Report commentOf(final ReportCreateRequest request, final Comment comment) {
        return Report.builder()
                .content(request.getContent())
                .reportType(request.getReportType())
                .comment(comment)
                .member(getCurrentMember())
                .build();
    }

}
