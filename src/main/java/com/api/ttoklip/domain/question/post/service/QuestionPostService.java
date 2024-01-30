package com.api.ttoklip.domain.question.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.question.image.service.ImageService;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.dto.response.QuestionSingleResponse;
import com.api.ttoklip.domain.question.post.repository.QuestionRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionPostService {

    private final QuestionRepository questionRepository;
    private final ImageService imageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Question findQuestionById(final Long postId) {
        return questionRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUNT));
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Long register(final QuestionCreateRequest request) {

        Question question = Question.of(request);
        questionRepository.save(question);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(question, uploadImages);
        }
        return question.getId();
    }

    private void registerImages(final Question question, final List<MultipartFile> multipartFiles) {
        List<String> uploadUrls = getImageUrls(multipartFiles);
        uploadUrls.forEach(uploadUrl -> imageService.register(question, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3FileUploader.uploadMultipartFiles(multipartFiles);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    public QuestionSingleResponse getSinglePost(final Long postId) {

        Question questionWithImgAndComment = questionRepository.findByIdFetchJoin(postId);
        QuestionSingleResponse questionSingleResponse = QuestionSingleResponse.from(questionWithImgAndComment);
        return questionSingleResponse;
    }

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public void report(final Long postId, final ReportCreateRequest request) {
        Question question = findQuestionById(postId);
        reportService.reportQuestion(request, question);
    }


    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

}
