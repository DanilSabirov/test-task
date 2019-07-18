package com.haulmont.testtask.gui;

import com.haulmont.testtask.database.ConnectionManager;
import com.haulmont.testtask.database.dao.PatientDAO;
import com.haulmont.testtask.database.entity.Patient;
import com.haulmont.testtask.database.entity.PatientImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    final HorizontalLayout main = new HorizontalLayout(new Label("Patients"));

    private PatientForm patientForm = null;

    Grid patientGrid = new Grid();

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    private List<Patient> patients = null;

    private Connection connection = null;

    private PatientDAO patientDAO = null;

    @Override
    protected void init(VaadinRequest request) {
        try {
            initPersistence();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        initPatientsGrid();

        try {
            updatePatientsGrid();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initMain();

        setContent(main);
    }

    private void initMain() {
        main.addComponents(patientGrid, patientForm);
        main.setMargin(true);
        main.setSpacing(true);
        main.setSizeFull();
        main.setExpandRatio(patientGrid, 1);
    }

    private void initPersistence() throws SQLException{
        connection = connectionManager.getConnection();
        patientDAO = new PatientDAO(connection);
    }

    private void initPatientsGrid() {
        patientForm = new PatientForm(this, patientDAO);

        patientGrid.setSizeFull();
        patientGrid.setColumns("name", "surname", "patronymic", "phoneNumber");
        patientGrid.setColumnOrder("name", "surname", "patronymic");
        patientGrid.addSelectionListener(event -> {
            if (!event.getSelected().isEmpty()) {
                PatientImpl patient = (PatientImpl) event.getSelected().iterator().next();
                patientForm.setPatient(patient);
            }

        });
    }

    public void updatePatientsGrid() throws SQLException {
        patients = patientDAO.getAll();
        patientGrid.setContainerDataSource(new BeanItemContainer<Patient>(Patient.class, patients));
    }
}