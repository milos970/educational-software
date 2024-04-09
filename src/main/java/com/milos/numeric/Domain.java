package com.milos.numeric;

public enum Domain {
    TEACHER_DOMAIN("fri.uniza.sk"),
    STUDENT_DOMAIN("stud.uniza.sk");
    private final String domain;

    Domain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
