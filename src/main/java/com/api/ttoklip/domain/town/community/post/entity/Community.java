package com.api.ttoklip.domain.town.community.post.entity;

import com.api.ttoklip.domain.town.cart.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateCommunityPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
