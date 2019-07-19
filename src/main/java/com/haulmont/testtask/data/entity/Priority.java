package com.haulmont.testtask.data.entity;

public enum Priority {
    NORMAL("Normal"),
    CITO("Cito"),
    STATIM("Statim");

    private String priority;

    Priority(String priority) {
        this.priority = priority;
    }
}
