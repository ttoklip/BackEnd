package com.api.ttoklip.domain.question.facade;

import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.question.comment.dto.response.QuestionCommentResponse;
import com.api.ttoklip.domain.question.comment.service.QuestionCommentService;
import com.api.ttoklip.domain.question.image.service.QuestionImageService;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.dto.response.QuestionSingleResponse;
import com.api.ttoklip.domain.question.post.service.QuestionCommonService;
import com.api.ttoklip.domain.question.post.service.QuestionPostService;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostFacade {

    private final QuestionPostService questionPostService;
    private final QuestionCommonService questionCommonService;
    private final QuestionImageService questionImageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;
    private final QuestionCommentService questionCommentService;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Question getQuestion(final Long postId) {
        return questionCommonService.getQuestion(postId);
    }

    public void checkEditPermission(final Question question) {
        questionCommonService.checkEditPermission(question);
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    @CheckBadWordCreate
    public Message register(final QuestionCreateRequest request) {
        Question question = Question.from(request);
        questionPostService.saveQuestion(question);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(question, uploadImages);
        }

        return Message.registerPostSuccess(Question.class, question.getId());
    }

    private void registerImages(final Question question, final List<MultipartFile> multipartFiles) {
        List<String> uploadUrls = s3FileUploader.uploadMultipartFiles(multipartFiles);
        uploadUrls.forEach(uploadUrl -> questionImageService.register(question, uploadUrl));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public QuestionSingleResponse getSinglePost(final Long postId) {
        Question questionWithImg = questionPostService.findByIdFetchJoin(postId);

        // 현재 사용자가 좋아요를 눌렀는지 확인
        List<QuestionCommentResponse> commentResponses = questionCommentService.getCommentResponse(questionWithImg);

        QuestionSingleResponse questionSingleResponse = QuestionSingleResponse.of(questionWithImg,
                commentResponses);
        return questionSingleResponse;
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
        Question question = questionCommonService.getQuestion(postId);
        reportService.reportQuestion(request, question);

        return Message.reportPostSuccess(Question.class, postId);
    }
    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

}
