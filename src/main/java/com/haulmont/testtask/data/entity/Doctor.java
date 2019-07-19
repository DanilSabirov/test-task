package com.haulmont.testtask.data.entity;

public interface Doctor extends Entity{
    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    String getPatronymic();

    void setPatronymic(String patronymic);

    String getSpecialty();

    void setSpecialty(String specialty);
}
