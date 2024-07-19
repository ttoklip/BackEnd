package com.api.ttoklip.domain.town.cart.post.entity;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.image.entity.CartImage;
import com.api.ttoklip.domain.town.cart.itemUrl.entity.ItemUrl;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.editor.CartPostEditor;
import com.api.ttoklip.domain.town.cart.post.editor.CartPostEditor.CartPostEditorBuilder;
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
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    // todo 위치 기반으로
    private String location;

    private Long totalPrice;

    private String chatUrl;

    private Long partyMax;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    public static Cart of(final CartCreateRequest request, final Member member) {
        return Cart.builder()
                .location(request.getLocation())
                .totalPrice(request.getTotalPrice())
                .chatUrl(request.getChatUrl())
                .partyMax(request.getPartyMax())
                .content(request.getContent())
                .title(request.getTitle())
                .status(TradeStatus.IN_PROGRESS)
                .member(member)
                .build();
    }

    public void changeComplete() {
        this.status = TradeStatus.COMPLETED;//마감
    }

    public void changeProgress() {
        this.status = TradeStatus.IN_PROGRESS;
    }

    @Override
    public void deactivate() {
        super.deactivate();  // BaseEntity에 정의되어 있는 메소드
        this.cartImages.forEach(CartImage::deactivate);
        this.reports.forEach(Report::deactivate);
        this.cartComments.forEach(CartComment::deactivate);
        this.itemUrls.forEach(ItemUrl::deactivate);
    }


    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartImage> cartImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartComment> cartComments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ItemUrl> itemUrls = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartMember> cartMembers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public CartPostEditorBuilder toEditor() {
        return CartPostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final CartPostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }

    public void changeStatus(TradeStatus newStatus) {
        this.status = newStatus;
    }
}
