package org.konkuk.foodmeter.domain.foodImage;

public enum Grade {
    A("1++"),
    B("1+"),
    C("1"),
    D("2"),
    E("3"),
    ;
    private String detail;

    Grade(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return this.detail;
    }
}
