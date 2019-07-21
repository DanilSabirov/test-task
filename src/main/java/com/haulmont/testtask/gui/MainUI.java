package com.haulmont.testtask.gui;

import com.haulmont.testtask.data.ConnectionManager;
import com.haulmont.testtask.data.dao.DoctorDAO;
import com.haulmont.testtask.data.dao.PatientDAO;
import com.haulmont.testtask.data.dao.PrescriptionDAO;
import com.haulmont.testtask.gui.tabs.DoctorsTab;
import com.haulmont.testtask.gui.tabs.Observer;
import com.haulmont.testtask.gui.tabs.PatientsTab;
import com.haulmont.testtask.gui.tabs.PrescriptionTab;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.SQLException;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private final HorizontalLayout main = new HorizontalLayout();

    private final TabSheet tabs = new TabSheet();

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    private Connection connection = null;

    private PatientDAO patientDAO;

    private DoctorDAO doctorDAO;

    private PrescriptionDAO prescriptionDAO;

    @Override
    protected void init(VaadinRequest request) {
        try {
            initPersistence();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        //test

        initMain();

        //test

        setContent(main);
    }

    private void initMain() {
        PatientsTab patientsTab = new PatientsTab(this, patientDAO);
        DoctorsTab doctorsTab = new DoctorsTab(this, doctorDAO);
        PrescriptionTab prescriptionTab = new PrescriptionTab(this, prescriptionDAO, patientDAO, doctorDAO);
        tabs.addTab(patientsTab, "Patients");
        tabs.addTab(doctorsTab, "Doctors");
        tabs.addTab(prescriptionTab, "Prescriptions");

        tabs.addSelectedTabChangeListener(event -> {
            ((Observer) event.getTabSheet().getSelectedTab()).update();
        });

        main.addComponents(tabs);
        main.setMargin(true);
        main.setSpacing(true);
        main.setSizeFull();
    }

    private void initPersistence() throws SQLException{
        connection = connectionManager.getConnection();
        patientDAO = new PatientDAO(connection);
        doctorDAO = new DoctorDAO(connection);
        prescriptionDAO = new PrescriptionDAO(connection);
    }
}