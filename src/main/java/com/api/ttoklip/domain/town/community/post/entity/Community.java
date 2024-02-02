package com.api.ttoklip.domain.town.community.post.entity;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.town.cart.image.Image;
import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CommunityImage> communityImages = new ArrayList<>();

    @Builder
    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateCommunityPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();
}
