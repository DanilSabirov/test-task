package com.haulmont.testtask.database.entity;

public interface Doctor {
    long getId();

    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    String getPatronymic();

    void setPatronymic(String patronymic);

    String getSpecialty();

    void setSpecialty(String specialty);
}
