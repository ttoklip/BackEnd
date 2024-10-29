package com.api.ttoklip.domain.common.report.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.repository.ReportRepository;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final MemberService memberService;
    private final ReportRepository reportRepository;

    @Transactional
    public void reportQuestion(final ReportCreateRequest request, final Question question) {
        Report report = Report.questionOf(request, question);
        reportRepository.save(report);
    }


    @Transactional
    public void reportHoneyTip(final ReportCreateRequest request, final HoneyTip honeyTip, final Member currentMember) {
        Report report = Report.honeyTipOf(request, honeyTip, currentMember);
        reportRepository.save(report);
    }

    @Transactional
    public void reportCommunity(final ReportCreateRequest request, final Community community) {
        Report report = Report.communityOf(request, community);
        reportRepository.save(report);
    }

    @Transactional
    public void reportCart(final ReportCreateRequest request, final Cart cart) {
        Report report = Report.cartOf(request, cart);
        reportRepository.save(report);
    }

    @Transactional
    public void reportComment(final ReportCreateRequest request, final Comment comment) {
        Report report = Report.commentOf(request, comment);
        reportRepository.save(report);
    }

    @Transactional
    public void reportNewsletter(final ReportCreateRequest request, final Newsletter newsletter) {
        Report report = Report.newsletterOf(request, newsletter);
        reportRepository.save(report);
    }

    @Transactional
    public Message reportMember(final ReportCreateRequest request, final String reportedMemberNickName) {
        Member reportedMember = memberService.findByNickNameWithProfile(reportedMemberNickName);
        Report report = Report.memberOf(request, reportedMember);
        reportRepository.save(report);
        return Message.reportMemberSuccess(reportedMemberNickName);
    }

}
