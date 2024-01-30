package com.api.ttoklip.domain.question.post.image.service;

import com.api.ttoklip.domain.question.post.image.domain.Image;
import com.api.ttoklip.domain.question.post.image.repository.ImageRepository;
import com.api.ttoklip.domain.question.post.post.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void register(final Question question, final String uploadUrl) {
        Image newImage = Image.of(question, uploadUrl);
        imageRepository.save(newImage);
    }
}
