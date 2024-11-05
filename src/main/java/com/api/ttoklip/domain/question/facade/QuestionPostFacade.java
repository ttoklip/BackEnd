package com.api.ttoklip.domain.question.facade;

import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.question.controller.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.controller.dto.response.QuestionCommentResponse;
import com.api.ttoklip.domain.question.controller.dto.response.QuestionSingleResponse;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.service.QuestionCommentLikeService;
import com.api.ttoklip.domain.question.service.QuestionCommentService;
import com.api.ttoklip.domain.question.service.QuestionImageService;
import com.api.ttoklip.domain.question.service.QuestionPostService;
import com.api.ttoklip.global.success.Message;
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

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    @CheckBadWordCreate
    public Message register(final QuestionCreateRequest request,final Long currentMemberId) {
        Member currentMember = memberService.findById(currentMemberId);
        Question question = Question.of(request, currentMember);
        questionPostService.saveQuestion(question);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(question, uploadImages);
        }

        return Message.registerPostSuccess(Question.class, question.getId());
    }

    private void registerImages(final Question question, final List<MultipartFile> multipartFiles) {
        List<String> uploadUrls = questionPostService.uploadImages(multipartFiles);
        uploadUrls.forEach(uploadUrl -> questionImageService.register(question, uploadUrl));
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

    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */

    public CategoryResponses getDefaultCategoryRead() {
        List<Question> houseWorkQuestions = questionPostService.getHouseWork();
        List<Question> recipeQuestions = questionPostService.getRecipe();
        List<Question> safeLivingQuestions = questionPostService.getSafeLiving();
        List<Question> welfarePolicyQuestions = questionPostService.getWelfarePolicy();

        return CategoryResponses.builder()
                .housework(convertToTitleResponses(houseWorkQuestions))
                .cooking(convertToTitleResponses(recipeQuestions))
                .safeLiving(convertToTitleResponses(safeLivingQuestions))
                .welfarePolicy(convertToTitleResponses(welfarePolicyQuestions))
                .build();
    }

    private List<TitleResponse> convertToTitleResponses(final List<Question> questions) {
        return questions.stream()
                .map(TitleResponse::questionOf)
                .toList();
    }

    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */

    // ------------------------------------ 메인 페이지 꿀팁공유해요 카테고리별 페이징 조회 ------------------------------------

    public CategoryPagingResponse matchCategoryPaging(final Category category, final Pageable pageable) {
        Page<Question> questions = questionPostService.matchCategoryPaging(category, pageable);
        List<TitleResponse> data = questions.stream()
                .map(TitleResponse::questionOf)
                .toList();

        return CategoryPagingResponse.builder()
                .data(data)
                .category(category)
                .totalPage(questions.getTotalPages())
                .totalElements(questions.getTotalElements())
                .isLast(questions.isLast())
                .isFirst(questions.isFirst())
                .build();
    }
    // ------------------------------------ 메인 페이지 꿀팁공유해요 카테고리별 페이징 조회 끝 ------------------------------------

    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Question question = questionPostService.getQuestion(postId);
        reportService.reportQuestion(request, question);

        return Message.reportPostSuccess(Question.class, postId);
    }
    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

}