package com.bluemoon.apartment.constant;

public enum FeeGroup {
    ANNUAL("Phí thường niên"),
    ARISING("Phí phát sinh");

    private final String displayName;

    FeeGroup(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}