package com.api.ttoklip.domain.town.community.image.service;

import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.image.repository.CommunityImageRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final CommunityImageRepository communityImageRepository;

    @Transactional
    public void register(final Community question, final String uploadUrl) {
        CommunityImage newCommunityImage = CommunityImage.of(question, uploadUrl);
        communityImageRepository.save(newCommunityImage);
    }
}
