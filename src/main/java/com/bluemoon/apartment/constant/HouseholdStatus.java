package com.bluemoon.apartment.constant;

public enum HouseholdStatus {
    ACTIVE("Đang cư trú"),
    MOVED_OUT("Đã chuyển đi"),
    TEMPORARY_ABSENCE("Tạm vắng"),
    INACTIVE("Không còn hiệu lực");

    private final String displayName;

    HouseholdStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}