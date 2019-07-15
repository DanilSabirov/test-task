package com.haulmont.testtask.database.entity;

public enum Priority {
    NORMAL("Normal"),
    CITO("Cito"),
    STATIM("Statim");

    private String priority;

    Priority(String priority) {
        this.priority = priority;
    }
}
