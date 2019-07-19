package com.haulmont.testtask.data.entity;

import java.time.LocalDate;

public class PrescriptionImpl extends BaseEntity implements Prescription {
    private String description = "";

    private Patient patient;

    private Doctor doctor;

    private LocalDate creationDate;

    private int daysValidity = 1;

    private Priority priority = Priority.NORMAL;

    public PrescriptionImpl(long id, Patient patient, Doctor doctor) {
        super(id);
        creationDate = LocalDate.now();
    }

    public PrescriptionImpl(long id, String description, Patient patient, Doctor doctor, LocalDate creationDate, int daysValidity, Priority priority) {
        super(id);
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creationDate = creationDate;
        this.daysValidity = daysValidity;
        this.priority = priority;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Patient getPatient() {
        return patient;
    }

    @Override
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int getDaysValidity() {
        return daysValidity;
    }

    @Override
    public void setDaysValidity(int daysValidity) {
        this.daysValidity = daysValidity;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
