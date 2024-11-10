package com.domain.honeytip.domain;

import com.domain.common.base.BaseEntity;
import com.domain.common.vo.Category;
import com.domain.honeytip.domain.HoneyTipEditor.HoneyTipEditorBuilder;
import com.domain.honeytip.domain.request.HoneyTipCreate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HoneyTip extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipImage> honeyTipImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipUrl> honeyTipUrls = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipComment> honeyTipComments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipLike> honeyTipLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipScrap> honeyTipScraps = new ArrayList<>();

    public static HoneyTip of(final HoneyTipCreate create, final Member member) {
        return HoneyTip.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .member(member)
                .category(create.getCategory())
                .build();
    }

    public HoneyTipEditorBuilder toEditor() {
        return HoneyTipEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final HoneyTipEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }

    @Override
    public void deactivate() {
        super.deactivate(); // HoneyTip 엔티티 비활성화
        deactivateHoneyTipUrls(); // HoneyTipUrl 엔티티들을 비활성화
        deactivateHoneyTipImages(); // HoneyTipImage 엔티티들을 비활성화
    }

    private void deactivateHoneyTipUrls() {
        honeyTipUrls.forEach(BaseEntity::deactivate);
    }

    private void deactivateHoneyTipImages() {
        honeyTipImages.forEach(BaseEntity::deactivate);
    }

}
