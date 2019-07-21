package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.entity.Patient;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.regex.Pattern;

public class PatientForm extends FormLayout implements EditableForm<Patient> {

    private TextField name = new TextField("Name");

    private TextField surname = new TextField("Surname");

    private TextField patronymic = new TextField("Patronymic");

    private TextField phoneNumber = new TextField("Phone number");

    private Patient patient;

    public PatientForm() {
        setSizeUndefined();

        StringLengthValidator stringLengthValidator = new StringLengthValidator("Min length 2", 2, 255, false);
        AbstractStringValidator notNumberValidator = new AbstractStringValidator("Incorrect") {
            @Override
            protected boolean isValidValue(String s) {
                Pattern p = Pattern.compile("\\D+");
                return p.matcher(s).matches();
            }
        };

        name.setMaxLength(255);
        surname.setMaxLength(255);
        patronymic.setMaxLength(255);

        name.setMaxLength(255);
        name.addValidator(stringLengthValidator);
        name.addValidator(notNumberValidator);

        surname.setMaxLength(255);
        surname.addValidator(stringLengthValidator);
        surname.addValidator(notNumberValidator);

        patronymic.setMaxLength(255);
        patronymic.addValidator(stringLengthValidator);
        patronymic.addValidator(notNumberValidator);

        phoneNumber.setMaxLength(11);
        phoneNumber.addValidator(new AbstractStringValidator("Number length 6-11") {
            @Override
            protected boolean isValidValue(String s) {
                Pattern p = Pattern.compile("\\d{6,11}");
                return p.matcher(s).matches();
            }
        });

        addComponents(surname, name, patronymic, phoneNumber);

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

    @Override
    public boolean isValid() {
        return name.isValid() && surname.isValid() && patronymic.isValid() && phoneNumber.isValid();
    }

    public Patient get() {
        patient.setName(name.getValue().trim());
        patient.setSurname(surname.getValue().trim());
        patient.setPatronymic(patronymic.getValue().trim());
        patient.setPhoneNumber(phoneNumber.getValue().trim());

        return patient;
    }
}
