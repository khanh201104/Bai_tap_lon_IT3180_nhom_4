package com.bluemoon.apartment.constant;

public enum PaymentStatus {
    UNPAID("Chưa đóng"),
    PAID("Đã đóng"),
    OVERDUE("Quá hạn"),
    CANCELLED("Đã hủy");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}