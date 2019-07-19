package com.haulmont.testtask.data.entity;

import java.time.LocalDate;

public interface Prescription extends Entity {
    String getDescription();

    void setDescription(String description);

    Patient getPatient();

    void setPatient(Patient patient);

    Doctor getDoctor();

    void setDoctor(Doctor doctor);

    LocalDate getCreationDate();

    void setCreationDate(LocalDate creationDate);

    int getDaysValidity();

    void setDaysValidity(int daysValidity);

    Priority getPriority();

    void setPriority(Priority priority);
}
