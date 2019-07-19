package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.entity.Doctor;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class DoctorForm extends FormLayout implements EditableForm<Doctor> {

    private TextField name = new TextField("Name");

    private TextField surname = new TextField("Surname");

    private TextField patronymic = new TextField("Patronymic");

    private TextField specialty = new TextField("Specialty");

    private Doctor doctor;

    public DoctorForm() {
        setSizeUndefined();
        addComponents(name, surname, patronymic, specialty);

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
        doctor.setName(name.getValue());
        doctor.setSurname(surname.getValue());
        doctor.setPatronymic(patronymic.getValue());
        doctor.setSpecialty(specialty.getValue());

        return doctor;
    }
}
