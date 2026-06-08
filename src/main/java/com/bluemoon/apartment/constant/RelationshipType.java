package com.bluemoon.apartment.constant;

public enum RelationshipType {
    HEAD("Chủ hộ"),
    SPOUSE("Vợ/chồng"),
    CHILD("Con"),
    PARENT("Cha/mẹ"),
    SIBLING("Anh/chị/em"),
    OTHER("Khác");

    private final String displayName;

    RelationshipType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}