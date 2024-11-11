package com.api.question.application;

import com.api.common.upload.Uploader;
import com.api.global.success.Message;
import com.api.question.presentation.dto.request.QuestionWebCreate;
import com.api.question.presentation.dto.response.QuestionCommentResponse;
import com.api.question.presentation.dto.response.QuestionSingleResponse;
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