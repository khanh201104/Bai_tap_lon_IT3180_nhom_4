package com.bluemoon.apartment.constant;

public enum FeeCycleType {
    MONTHLY("Đóng theo tháng"),
    YEARLY("Đóng theo năm"),
    ONE_TIME("Không lặp");

    private final String displayName;

    FeeCycleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}