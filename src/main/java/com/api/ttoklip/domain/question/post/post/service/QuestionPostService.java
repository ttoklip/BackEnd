package com.api.ttoklip.domain.question.post.post.service;

import com.api.ttoklip.domain.question.post.image.service.ImageService;
import com.api.ttoklip.domain.question.post.post.domain.Question;
import com.api.ttoklip.domain.question.post.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.post.dto.request.QuestionEditRequest;
import com.api.ttoklip.domain.question.post.post.dto.response.QuestionWithCommentResponse;
import com.api.ttoklip.domain.question.post.post.repository.QuestionRepository;
import com.api.ttoklip.global.s3.S3FileUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuestionPostService {

    private final QuestionRepository questionRepository;
    private final ImageService imageService;
    private final S3FileUploader s3FileUploader;

    /* -------------------------------------------- CREATE -------------------------------------------- */
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


    public QuestionWithCommentResponse getSinglePost(final Long postId) {
        return null;
    }

    public void edit(final Long postId, final QuestionEditRequest request) {
    }

    public void delete(final Long postId) {
    }
}
