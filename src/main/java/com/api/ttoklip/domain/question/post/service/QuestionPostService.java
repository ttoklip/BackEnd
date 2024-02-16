package com.api.ttoklip.domain.question.post.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.image.service.QuestionImageService;
import com.api.ttoklip.domain.question.like.repository.CommentLikeRepository;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.dto.response.QuestionSingleResponse;
import com.api.ttoklip.domain.question.post.repository.QuestionDefaultRepository;
import com.api.ttoklip.domain.question.post.repository.QuestionRepository;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostService {

    private final QuestionRepository questionRepository;
    private final QuestionDefaultRepository questionDefaultRepository;
    private final QuestionImageService questionImageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;
    private final QuestionCommonService questionCommonService;
    private final CommentLikeRepository commentLikeRepository;

//    /* -------------------------------------------- COMMON -------------------------------------------- */
//    public Question findQuestionById(final Long postId) {
//        return questionRepository.findById(postId)
//                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
//    }
//
//    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final QuestionCreateRequest request) {

        Question question = Question.from(request);
        questionRepository.save(question);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(question, uploadImages);
        }

        return Message.registerPostSuccess(Question.class, question.getId());
    }

    private void registerImages(final Question question, final List<MultipartFile> multipartFiles) {
        List<String> uploadUrls = getImageUrls(multipartFiles);
        uploadUrls.forEach(uploadUrl -> questionImageService.register(question, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3FileUploader.uploadMultipartFiles(multipartFiles);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public QuestionSingleResponse getSinglePost(final Long postId, final Long commentId) {
        Question questionWithImg = questionRepository.findByIdFetchJoin(postId);
        List<QuestionComment> activeComments = questionRepository.findActiveCommentsByQuestionId(postId);

        // 현재 사용자 ID를 가져옴
        Long currentMemberId = getCurrentMember().getId();

        // 현재 사용자가 좋아요를 눌렀는지 확인
        boolean likedByCurrentUser = commentLikeRepository.existsByQuestionCommentIdAndMemberId(commentId, currentMemberId);

        QuestionSingleResponse questionSingleResponse = QuestionSingleResponse.of(questionWithImg,
                activeComments, likedByCurrentUser);
        return questionSingleResponse;
    }

    /* -------------------------------------------- 단건 READ 끝-------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */

    public CategoryResponses getDefaultCategoryRead() {
        List<Question> houseWorkQuestions = questionDefaultRepository.getHouseWork();
        List<Question> recipeQuestions = questionDefaultRepository.getRecipe();
        List<Question> safeLivingQuestions = questionDefaultRepository.getSafeLiving();
        List<Question> welfarePolicyQuestions = questionDefaultRepository.getWelfarePolicy();

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
        Page<Question> questions = questionRepository.matchCategoryPaging(category, pageable);
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
