package com.api.ttoklip.domain.town.cart;

import com.api.ttoklip.domain.town.cart.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private int party;

    private int totalPrice;

    private String location;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    private String productLink;

    private String chatLink;

    private LocalDateTime deadline;

    public void updateDeadlineTime(int days, int hours) {
        LocalDateTime selectedDateTime = LocalDateTime.now().plusDays(days).plusHours(hours);
        updateDeadline(selectedDateTime);
    }

    private void updateDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
