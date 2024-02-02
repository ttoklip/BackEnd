package com.api.ttoklip.domain.honeytip.post.post.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.honeytip.post.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneyTipCreateReq;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HoneyTip extends BaseEntity {
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

    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL)
    private List<HoneyTipImage> honeyTipImageList = new ArrayList<>();

    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL)
    private List<HoneyTipUrl> honeyTipUrlList = new ArrayList<>();

    public static HoneyTip from(final HoneyTipCreateReq req) {
        return HoneyTip.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .category(req.getCategory())
                .build();
    }

    public void updateHoneyTip(String title, String content, Category category, List<HoneyTipImage> honeyTipImages,
                               List<HoneyTipUrl> honeyTipUrls) {
        this.title = title;
        this.content = content;
        this.category = category;

        updateImages(honeyTipImages);
        updateUrls(honeyTipUrls);
    }

    public void updateImages(List<HoneyTipImage> honeyTipImages) {
        // 기존 이미지 목록에서 더 이상 사용하지 않는 이미지 삭제
        this.honeyTipImageList.removeIf(existingImage ->
                honeyTipImages.stream()
                        .noneMatch(honeytipImage -> honeytipImage.getUrl().equals(existingImage.getUrl())));

        // 새 이미지 목록에서 기존에 없는 이미지 추가
        for (HoneyTipImage honeytipImage : honeyTipImages) {
            if (this.honeyTipImageList.stream()
                    .noneMatch(existingImage -> existingImage.getUrl().equals(honeytipImage.getUrl()))) {
                this.honeyTipImageList.add(honeytipImage);
                honeytipImage.updateHoneyTip(this); // 연관 관계 설정
            }
        }
    }

    public void updateUrls(List<HoneyTipUrl> honeyTipUrls) {

        // 기존 URL 목록에서 더 이상 사용하지 않는 URL 삭제
        this.honeyTipUrlList.removeIf(existingUrl ->
                honeyTipUrls.stream().noneMatch(honeytipUrl -> honeytipUrl.getUrl().equals(existingUrl.getUrl())));

        // 새 URL 목록에서 기존에 없는 URL 추가
        for (HoneyTipUrl honeytipUrl : honeyTipUrls) {
            if (this.honeyTipUrlList.stream()
                    .noneMatch(existingUrl -> existingUrl.getUrl().equals(honeytipUrl.getUrl()))) {
                this.honeyTipUrlList.add(honeytipUrl);
                honeytipUrl.updateHoneyTip(this); // 연관 관계 설정
            }
        }
    }
}
