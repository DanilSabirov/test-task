package com.haulmont.testtask.data.entity;

import java.util.Objects;

public class PatientImpl extends BaseEntity implements Patient {
    private String name = "";

    private String surname = "";

    private String patronymic = "";

    private String phoneNumber = "";

    public PatientImpl(long id) {
        super(id);
    }

    public PatientImpl(long id, String name, String surname, String patronymic, String phoneNumber) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
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
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientImpl)) return false;
        PatientImpl patient = (PatientImpl) o;
        return Objects.equals(getName(), patient.getName()) &&
                Objects.equals(getSurname(), patient.getSurname()) &&
                Objects.equals(getPatronymic(), patient.getPatronymic()) &&
                Objects.equals(getPhoneNumber(), patient.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getPatronymic(), getPhoneNumber());
    }
}
