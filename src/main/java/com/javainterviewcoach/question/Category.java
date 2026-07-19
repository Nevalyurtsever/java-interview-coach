package com.javainterviewcoach.question;

public enum Category {
    JAVA_CORE("Java Core"),
    OOP("OOP"),
    COLLECTIONS("Collections"),
    SPRING_BOOT("Spring Boot"),
    SQL("SQL");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
