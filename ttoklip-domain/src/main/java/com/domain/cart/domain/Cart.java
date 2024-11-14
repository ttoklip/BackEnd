package com.domain.cart.domain;

import com.domain.cart.domain.CartPostEditor.CartPostEditorBuilder;
import com.domain.cart.domain.vo.TradeStatus;
import com.domain.common.base.BaseEntity;
import com.domain.report.domain.Report;
import com.domain.member.domain.Member;
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
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    private String location;

    private Long totalPrice;

    private String chatUrl;

    private Long partyMax;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    public static Cart from(final CartCreate create) {
        return Cart.builder()
                .location(create.location())
                .totalPrice(create.totalPrice())
                .chatUrl(create.chatUrl())
                .partyMax(create.partyMax())
                .content(create.content())
                .title(create.title())
                .status(TradeStatus.IN_PROGRESS)
                .member(create.member())
                .build();
    }

    public void changeComplete() {
        this.status = TradeStatus.COMPLETED;//마감
    }

    public void changeStatusToProgress() {
        this.status = TradeStatus.IN_PROGRESS;
    }

    @Override
    public void deactivate() {
        super.deactivate();
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
