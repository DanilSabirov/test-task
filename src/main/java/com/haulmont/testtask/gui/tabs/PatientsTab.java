package com.haulmont.testtask.gui.tabs;


import com.haulmont.testtask.data.dao.PatientDAO;
import com.haulmont.testtask.data.entity.Patient;
import com.haulmont.testtask.data.entity.PatientImpl;
import com.haulmont.testtask.gui.CreateWindow;
import com.haulmont.testtask.gui.EditWindow;
import com.haulmont.testtask.gui.forms.PatientForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class PatientsTab extends VerticalLayout implements Observer {
    private UI mainUI;

    private Grid patientsGrid = new Grid();

    private PatientDAO patientDAO;

    private List<Patient> patients;

    public PatientsTab(UI mainUI, PatientDAO patientDAO) {
        this.mainUI = mainUI;
        this.patientDAO = patientDAO;

        HorizontalLayout buttonLayout = getButtons();
        initPatientsGrid();

        addComponents(buttonLayout, patientsGrid);
        setExpandRatio(patientsGrid, 1);
        setMargin(true);
        setSpacing(true);
        setSizeFull();

        update();
    }

    private void initPatientsGrid() {
        patientsGrid.setSizeFull();
        patientsGrid.setColumns("name", "surname", "patronymic", "phoneNumber");
        patientsGrid.setColumnOrder("surname", "name", "patronymic");
    }

    private HorizontalLayout getButtons() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button edit = new Button("Edit");
        edit.addClickListener(event -> {
            if (patientsGrid.getSelectedRow() != null) {
                Patient patient = (Patient) patientsGrid.getSelectedRow();
                mainUI.addWindow(new EditWindow(this, new PatientForm(), patient, patientDAO));
            }
        });

        Button delete = new Button("Delete");
        delete.addClickListener(event -> {
            if (patientsGrid.getSelectedRow() != null) {
                Patient patient = (Patient) patientsGrid.getSelectedRow();
                try {
                    if (patientDAO.canDelete(patient)) {
                        patientDAO.delete(patient);
                    }
                    else {
                        Notification.show("Patient has prescription!", Notification.Type.ERROR_MESSAGE);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                update();
            }
        });

        Button create = new Button("Create");
        create.addClickListener(event -> {
            Patient patient = new PatientImpl(0);
            mainUI.addWindow(new CreateWindow(this, patient, new PatientForm(), patientDAO));
        });

        buttonLayout.addComponents(create, edit, delete);
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    public void update() {
        try {
            patients = patientDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        patientsGrid.setContainerDataSource(new BeanItemContainer<Patient>(Patient.class, patients));
        patientsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }
}
