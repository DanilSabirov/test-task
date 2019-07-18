package com.haulmont.testtask.gui;

import com.haulmont.testtask.database.dao.PatientDAO;
import com.haulmont.testtask.database.entity.PatientImpl;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class PatientForm extends FormLayout {
    private TextField name = new TextField("Name");

    private TextField surname = new TextField("Surname");

    private TextField patronymic = new TextField("Patronymic");

    private TextField phoneNumber = new TextField("Patronymic");

    private Button save = new Button("Save");

    private Button cancel = new Button("Cancel");

    private PatientImpl patient;

    private PatientDAO patientDAO;

    private MainUI mainUI;

    public PatientForm(MainUI mainUI, PatientDAO patientDAO) {
        this.mainUI = mainUI;
        this.patientDAO = patientDAO;

        save.addClickListener(listener -> save());
        cancel.addClickListener(listener -> cancel());

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        buttons.setSpacing(true);
        addComponents(name, surname, patronymic, phoneNumber, buttons);

        setVisible(false);
    }


    private void save() {
        patient.setName(name.getValue());
        patient.setSurname(surname.getValue());
        patient.setPatronymic(patronymic.getValue());
        patient.setPhoneNumber(phoneNumber.getValue());

        try {
            patientDAO.update(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            mainUI.updatePatientsGrid();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setVisible(false);
    }

    private void cancel() {
        setVisible(false);
    }

    public void setPatient(PatientImpl patient) {
        this.patient = patient;

        name.setValue(patient.getName());
        surname.setValue(patient.getSurname());
        patronymic.setValue(patient.getPatronymic());
        phoneNumber.setValue(patient.getPhoneNumber());

        setVisible(true);
    }

}
