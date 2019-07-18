package com.haulmont.testtask.database.entity;

public class DoctorImpl extends BaseEntity implements Doctor {
    private String name;

    private String surname;

    private String patronymic;

    private String specialty;

    public DoctorImpl(long id) {
        super(id);
    }

    public DoctorImpl(long id, String name, String surname, String patronymic, String specialty) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.specialty = specialty;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String getSpecialty() {
        return specialty;
    }

    @Override
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}