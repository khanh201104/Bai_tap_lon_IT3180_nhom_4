package com.bluemoon.apartment.constant;

public enum FeeCalculationType {
    FIXED("Cố định"),
    BY_AREA("Theo diện tích căn hộ"),
    BY_RESIDENT("Theo số hộ dân"),
    MANUAL("Nhập thủ công");

    private final String displayName;

    FeeCalculationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}