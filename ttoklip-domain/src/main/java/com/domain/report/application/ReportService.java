package com.domain.report.application;


import com.domain.cart.domain.Cart;
import com.domain.comment.domain.Comment;
import com.domain.report.domain.Report;
import com.domain.report.domain.ReportCreate;
import com.domain.report.domain.ReportRepository;
import com.domain.community.domain.Community;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.domain.Member;
import com.domain.newsletter.domain.Newsletter;
import com.domain.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void reportQuestion(final ReportCreate request, final Question question, final Member member) {
        Report report = Report.questionOf(request, question, member);
        reportRepository.save(report);
    }

    @Transactional
    public void reportHoneyTip(final ReportCreate request, final HoneyTip honeyTip, final Member member) {
        Report report = Report.honeyTipOf(request, honeyTip, member);
        reportRepository.save(report);
    }

    @Transactional
    public void reportCommunity(final ReportCreate request, final Community community, final Member member) {
        Report report = Report.communityOf(request, community, member);
        reportRepository.save(report);
    }

    @Transactional
    public void reportCart(final ReportCreate request, final Cart cart, final Member member) {
        Report report = Report.cartOf(request, cart, member);
        reportRepository.save(report);
    }

    @Transactional
    public void reportComment(final ReportCreate request, final Comment comment, final Member member) {
        Report report = Report.commentOf(request, comment, member);
        reportRepository.save(report);
    }

    @Transactional
    public void reportNewsletter(final ReportCreate request, final Newsletter newsletter, final Member member) {
        Report report = Report.newsletterOf(request, newsletter, member);
        reportRepository.save(report);
    }

//    @Transactional
//    public Message reportMember(final ReportCreate request, final String reportedMemberNickName, final Member reporter) {
//        Member reportedMember = memberService.findByNickNameWithProfile(reportedMemberNickName);
//        Report report = Report.memberOf(request, reportedMember, reporter);
//        reportRepository.save(report);
//        return Message.reportMemberSuccess(reportedMemberNickName);
//    }

}
