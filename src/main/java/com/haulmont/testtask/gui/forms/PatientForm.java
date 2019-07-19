package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.entity.Patient;
import com.vaadin.ui.*;

public class PatientForm extends FormLayout implements EditableForm<Patient> {

    private TextField name = new TextField("Name");

    private TextField surname = new TextField("Surname");

    private TextField patronymic = new TextField("Patronymic");

    private TextField phoneNumber = new TextField("Phone number");

    private Patient patient;

    public PatientForm() {
        setSizeUndefined();
        addComponents(name, surname, patronymic, phoneNumber);

        setVisible(false);
    }

    public void set(Patient patient) {
        this.patient = patient;

        name.setValue(patient.getName());
        surname.setValue(patient.getSurname());
        patronymic.setValue(patient.getPatronymic());
        phoneNumber.setValue(patient.getPhoneNumber());

        setVisible(true);
    }

    public Patient get() {
        patient.setName(name.getValue());
        patient.setSurname(surname.getValue());
        patient.setPatronymic(patronymic.getValue());
        patient.setPhoneNumber(phoneNumber.getValue());

        return patient;
    }
}
