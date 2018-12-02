package com.example.springbootexample.Mail.Config;

public enum
MailContentEnum {
    TEXT("text"),
    HTML("text/html;charset=UTF-8");

    private String value;

    MailContentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
