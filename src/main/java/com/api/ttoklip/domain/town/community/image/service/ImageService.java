package com.api.ttoklip.domain.town.community.image.service;

import com.api.ttoklip.domain.town.community.image.entity.Image;
import com.api.ttoklip.domain.town.community.image.repository.ImageRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void register(final Community question, final String uploadUrl) {
        Image newImage = Image.of(question, uploadUrl);
        imageRepository.save(newImage);
    }
}
