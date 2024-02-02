package com.api.ttoklip.domain.common.report.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.repository.ReportRepository;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void reportQuestion(final ReportCreateRequest request, final Question question) {
        Report report = Report.questionOf(request, question);
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
}
