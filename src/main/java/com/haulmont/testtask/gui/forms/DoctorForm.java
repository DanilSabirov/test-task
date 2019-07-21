package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.entity.Doctor;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import java.util.regex.Pattern;

public class DoctorForm extends FormLayout implements EditableForm<Doctor> {

    private TextField name = new TextField("Name");

    private TextField surname = new TextField("Surname");

    private TextField patronymic = new TextField("Patronymic");

    private TextField specialty = new TextField("Specialty");

    private Doctor doctor;

    public DoctorForm() {
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
        name.addValidator(stringLengthValidator);
        name.addValidator(notNumberValidator);

        surname.setMaxLength(255);
        surname.addValidator(stringLengthValidator);
        surname.addValidator(notNumberValidator);

        patronymic.setMaxLength(255);
        patronymic.addValidator(stringLengthValidator);
        patronymic.addValidator(notNumberValidator);

        specialty.setMaxLength(255);
        specialty.addValidator(stringLengthValidator);

        addComponents(surname, name, patronymic, specialty);

        setVisible(false);
    }

    public void set(Doctor doctor) {
        this.doctor = doctor;

        name.setValue(doctor.getName());
        surname.setValue(doctor.getSurname());
        patronymic.setValue(doctor.getPatronymic());
        specialty.setValue(doctor.getSpecialty());

        setVisible(true);
    }


    public Doctor get() {
        doctor.setName(name.getValue().trim());
        doctor.setSurname(surname.getValue().trim());
        doctor.setPatronymic(patronymic.getValue().trim());
        doctor.setSpecialty(specialty.getValue().trim());

        return doctor;
    }

    @Override
    public boolean isValid() {
        return name.isValid() && surname.isValid() && patronymic.isValid() && specialty.isValid();
    }
}
