package com.domain.cart.domain.vo;

public enum TradeStatus {
    IN_PROGRESS("진행 중"),
    COMPLETED("마감");

    private final String description;

    TradeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
