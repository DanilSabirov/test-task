package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.dao.DoctorDAO;
import com.haulmont.testtask.data.dao.PatientDAO;
import com.haulmont.testtask.data.entity.Doctor;
import com.haulmont.testtask.data.entity.Patient;
import com.haulmont.testtask.data.entity.Prescription;
import com.haulmont.testtask.data.entity.Priority;
import com.vaadin.data.validator.BigIntegerRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;

public class PrescriptionForm extends FormLayout implements EditableForm<Prescription> {
    private TextField description = new TextField("Description");

    private ComboBox patientComboBox = new ComboBox("Patient");

    private ComboBox doctorComboBox = new ComboBox("Doctor");

    private DateField creationDate = new DateField("Creation date");

    private TextField daysValidity = new TextField("Validity");

    private NativeSelect priority = new NativeSelect("Priority");

    private Prescription prescription;

    public PrescriptionForm(PatientDAO patientDAO, DoctorDAO doctorDAO) throws SQLException {
        setSizeUndefined();

        description.setMaxLength(8000);
        description.addValidator(new StringLengthValidator("Min length 1", 1, 8000, false));

        daysValidity.setMaxLength(9);
        daysValidity.setConverter(Integer.class);
        daysValidity.addValidator(new IntegerRangeValidator("Min value 1", 1, 999999999));
        daysValidity.setImmediate(true);

        patientComboBox.setNullSelectionAllowed(false);
        patientComboBox.addItems(patientDAO.getAll());
        patientComboBox.addValidator(new NullValidator("Select patient", false));

        doctorComboBox.setNullSelectionAllowed(false);
        doctorComboBox.addItems(doctorDAO.getAll());
        doctorComboBox.addValidator(new NullValidator("Select doctor", false));

        priority.addItems(Priority.values());
        priority.setNullSelectionAllowed(false);

        addComponents(description, patientComboBox, doctorComboBox, creationDate, daysValidity, priority);
        setVisible(false);
    }

    @Override
    public Prescription get() {
        prescription.setDescription(description.getValue().trim());
        prescription.setPatient((Patient) patientComboBox.getValue());
        prescription.setDoctor((Doctor) doctorComboBox.getValue());
        prescription.setCreationDate(new java.sql.Date(creationDate.getValue().getTime()).toLocalDate());
        prescription.setDaysValidity(new Integer(daysValidity.getValue()));
        prescription.setPriority((Priority) priority.getValue());

        return prescription;
    }

    @Override
    public void set(Prescription entity) {
        this.prescription = entity;

        description.setValue(prescription.getDescription());

        setComboboxValue(patientComboBox, entity.getPatient());
        setComboboxValue(doctorComboBox, entity.getDoctor());

        creationDate.setValue(Date.valueOf(entity.getCreationDate()));
        daysValidity.setValue(Integer.toString(entity.getDaysValidity()));
        priority.setValue(entity.getPriority());

        setVisible(true);
    }

    private <T> void setComboboxValue(ComboBox comboBox, T value) {
        Iterator<T> iterator = (Iterator<T>) comboBox.getItemIds().iterator();

        while (iterator.hasNext()) {
            T curValue = iterator.next();
            if (curValue.equals(value)) {
                comboBox.setValue(value);
                break;
            }
        }
    }

    public boolean isValid() {
        return description.isValid() && patientComboBox.isValid() && doctorComboBox.isValid() && daysValidity.isValid();
    }
}
