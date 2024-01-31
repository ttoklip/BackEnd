package com.api.ttoklip.domain.honeytip.post.post.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Honeytip")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Honeytip extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private Category category;

    @OneToMany(mappedBy = "honeytip", cascade = CascadeType.ALL)
    private List<HoneytipImage> honeytipImageList = new ArrayList<>();

    @OneToMany(mappedBy = "honeytip", cascade = CascadeType.ALL)
    private List<HoneytipUrl> honeytipUrlList = new ArrayList<>();


    @Builder
    public Honeytip(String title, String content, Category category, List<HoneytipImage> honeytipImageList, List<HoneytipUrl> honeytipUrlList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.honeytipImageList = honeytipImageList;
        this.honeytipUrlList = honeytipUrlList;
    }

    public void updateHoneytip(String title, String content, Category category, List<HoneytipImage> honeytipImages, List<HoneytipUrl> honeytipUrls) {
        this.title = title;
        this.content = content;
        this.category = category;

        updateImages(honeytipImages);
        updateUrls(honeytipUrls);
    }

    public void updateImages(List<HoneytipImage> honeytipImages) {
        // 기존 이미지 목록에서 더 이상 사용하지 않는 이미지 삭제
        this.honeytipImageList.removeIf(existingImage ->
                honeytipImages.stream().noneMatch(honeytipImage -> honeytipImage.getUrl().equals(existingImage.getUrl())));


        // 새 이미지 목록에서 기존에 없는 이미지 추가
        for (HoneytipImage honeytipImage : honeytipImages) {
            if (this.honeytipImageList.stream().noneMatch(existingImage -> existingImage.getUrl().equals(honeytipImage.getUrl()))) {
                this.honeytipImageList.add(honeytipImage);
                honeytipImage.updateHoneytip(this); // 연관 관계 설정
            }
        }
    }

    public void updateUrls(List<HoneytipUrl> honeytipUrls) {

        // 기존 URL 목록에서 더 이상 사용하지 않는 URL 삭제
        this.honeytipUrlList.removeIf(existingUrl ->
                honeytipUrls.stream().noneMatch(honeytipUrl -> honeytipUrl.getUrl().equals(existingUrl.getUrl())));

        // 새 URL 목록에서 기존에 없는 URL 추가
        for (HoneytipUrl honeytipUrl : honeytipUrls) {
            if (this.honeytipUrlList.stream().noneMatch(existingUrl -> existingUrl.getUrl().equals(honeytipUrl.getUrl()))) {
                this.honeytipUrlList.add(honeytipUrl);
                honeytipUrl.updateHoneytip(this); // 연관 관계 설정
            }
        }
    }
}
