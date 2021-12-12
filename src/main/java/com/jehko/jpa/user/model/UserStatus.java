package com.jehko.jpa.user.model;

public enum UserStatus {
    None, Using, Stop;

    int value;

    UserStatus() {

    }

    public int getValue() {
        return value;
    }
}
