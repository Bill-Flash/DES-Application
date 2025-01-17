package com.example.des.en;

public enum EnumMedia {
    FILE("From the file system"), INTERFACE("Through the interface");

    private String hint;
    EnumMedia(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return this.hint;
    }
}
