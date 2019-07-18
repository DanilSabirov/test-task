package com.haulmont.testtask.database.entity;

public interface Patient extends Entity{
    String getName();

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    String getPatronymic();

    void setPatronymic(String patronymic);

    String getPhoneNumber();

    void setPhoneNumber(String number);
}
