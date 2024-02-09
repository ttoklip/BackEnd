package com.api.ttoklip.domain.honeytip.post.service;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.repository.HoneyTipRepository;
import com.api.ttoklip.global.s3.S3FileUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommonService {

    private final S3FileUploader s3FileUploader;
    private final HoneyTipRepository honeytipRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public HoneyTip getHoneytip(final Long postId) {
        return honeytipRepository.findByIdActivated(postId);
    }

    public List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

}
