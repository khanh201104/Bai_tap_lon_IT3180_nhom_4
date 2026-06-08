package com.bluemoon.apartment.constant;

public enum VehicleStatus {
    ACTIVE("Đang gửi"),
    INACTIVE("Ngừng gửi");

    private final String displayName;

    VehicleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}