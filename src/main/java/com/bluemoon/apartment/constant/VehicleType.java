package com.bluemoon.apartment.constant;

public enum VehicleType {
    MOTORBIKE("Xe máy", 70000),
    CAR("Ô tô", 1200000),
    BICYCLE("Xe đạp", 30000),
    ELECTRIC_BIKE("Xe đạp điện", 100000);

    private final String displayName;
    private final Integer monthlyFee;

    VehicleType(String displayName, Integer monthlyFee) {
        this.displayName = displayName;
        this.monthlyFee = monthlyFee;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getMonthlyFee() {
        return monthlyFee;
    }
}