package com.jehko.jpa.user.model;

public enum UserStatus {
    None, Using, Stop, Lock;

    int value;

    UserStatus() {

    }

    public int getValue() {
        return value;
    }
}
