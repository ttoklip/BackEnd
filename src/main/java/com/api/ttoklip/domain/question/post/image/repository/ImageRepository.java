package com.api.ttoklip.domain.question.post.image.repository;

import com.api.ttoklip.domain.question.post.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
