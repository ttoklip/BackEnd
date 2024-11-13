package com.api.question.application;

import com.api.common.ReportWebCreate;
import com.api.common.upload.Uploader;
import com.api.global.success.Message;
import com.domain.common.vo.CategoryPagingResponse;
import com.domain.common.vo.Category;
import com.domain.common.vo.TitleResponse;
import com.api.question.presentation.dto.request.QuestionWebCreate;
import com.api.question.presentation.dto.response.QuestionCommentResponse;
import com.api.question.presentation.dto.response.QuestionSingleResponse;
import com.domain.common.report.domain.ReportCreate;
import com.domain.common.report.application.ReportService;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.question.application.QuestionCommentLikeService;
import com.domain.question.application.QuestionCommentService;
import com.domain.question.application.QuestionImageService;
import com.domain.question.application.QuestionPostService;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionComment;
import com.domain.question.domain.QuestionCreate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostFacade {

    private final QuestionPostService questionPostService;
    private final QuestionImageService questionImageService;
    private final ReportService reportService;
    private final QuestionCommentService questionCommentService;
    private final QuestionCommentLikeService questionCommentLikeService;
    private final MemberService memberService;
    private final Uploader uploader;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    @CheckBadWordCreate
    public Message register(final QuestionWebCreate request, final Long memberId) {
        Member member = memberService.getById(memberId);

        QuestionCreate create = QuestionCreate.of(request.getTitle(), request.getContent(), request.getCategory(), member);
        Question question = Question.from(create);
        questionPostService.saveQuestion(question);

        registerImages(request, question);
        return Message.registerPostSuccess(Question.class, question.getId());
    }

    private void registerImages(final QuestionWebCreate request, final Question question) {
        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            List<String> uploadUrls = uploader.uploadMultipartFiles(uploadImages);
            uploadUrls.forEach(uploadUrl -> questionImageService.register(question, uploadUrl));
        }
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public QuestionSingleResponse getSinglePost(final Long postId, final Long currentMemberId) {
        Question questionWithImg = questionPostService.findByIdFetchJoin(postId);
        List<QuestionComment> questionComments = questionCommentService.findQuestionCommentsByQuestionId(
                questionWithImg.getId());
        List<QuestionCommentResponse> commentResponses = toCommentResponsesWithLikeStatus(questionComments,
                currentMemberId);
        return QuestionSingleResponse.of(questionWithImg, commentResponses);
    }

    private List<QuestionCommentResponse> toCommentResponsesWithLikeStatus(List<QuestionComment> questionComments,
                                                                           Long currentMemberId) {
        return questionComments.stream()
                .map(questionComment -> {
                    boolean likedByCurrentUser = questionCommentLikeService.existsByQuestionCommentIdAndMemberId(
                            questionComment.getId(), currentMemberId);
                    return QuestionCommentResponse.from(questionComment, likedByCurrentUser);
                }).toList();
    }

    /* -------------------------------------------- 단건 READ 끝-------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportWebCreate request, final Long reporterId) {
        Question question = questionPostService.getQuestion(postId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportQuestion(create, question, reporter);
        return Message.reportPostSuccess(Question.class, postId);
    }
    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

}